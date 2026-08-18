package model.actions.turnActions;

import controller.Simulation;
import model.entities.Entity;
import model.entitymap.Coordinates;
import model.entitymap.EntityMap;
import model.actions.Action;
import model.spawning.EntityCreator;
import model.entities.EntityType;
import model.util.EntityMapUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TurnEntityCreator extends EntityCreator implements Action {

    private static final int MAX_TURNS_WITHOUT_FOOD = 4;
    private static final int MIN_FOOD_QUANTITY_TO_CREATE = 1;
    private static final int MIN_PREDATORS_QUANTITY_TO_CREATE = 3;

    private int withoutHerbivoreFoodCounter = 0;
    private int withoutPredatorFoodCounter = 0;

    @Override
    public void execute(EntityMap map) {
        Map<EntityType, Boolean> hasFoodForCreature = getEntityTypeBooleanMap(map);

        for (var entry : hasFoodForCreature.entrySet()) {

            if (withoutPredatorFoodCounter > MAX_TURNS_WITHOUT_FOOD) {
                addMissingFood(EntityType.PREDATOR, map);
                withoutPredatorFoodCounter = 0;
            }
            if (withoutHerbivoreFoodCounter > MAX_TURNS_WITHOUT_FOOD) {
                addMissingFood(EntityType.HERBIVORE, map);
                withoutHerbivoreFoodCounter = 0;
            }
            if (hasNoPredators(map)) {
                addPredators(map);
            }
            if (hasNoFood(entry)) {
                increaseCounter(entry.getKey());
            }
        }
        Simulation.setEntityMap(map);
    }

    private static Map<EntityType, Boolean> getEntityTypeBooleanMap(EntityMap entityMap) {
        boolean hasPredatorFood = false;
        boolean hasHerbivoreFood = false;

        for (var entity : EntityMapUtils.getEntitiesBy(Entity.class, entityMap)) {
            switch (entity.getType()) {
                case EntityType.HERBIVORE:
                    hasPredatorFood = true;
                    break;
                case EntityType.GRASS:
                    hasHerbivoreFood = true;
                    break;
            }
        }
        return Map.of(EntityType.HERBIVORE, hasHerbivoreFood, EntityType.PREDATOR, hasPredatorFood);
    }

    private void addMissingFood(EntityType hungryCreature, EntityMap map) {
        int halfMapSize = (map.getWidth()+ map.getHeight()) / 2;
        int foodQuantity = RANDOM.nextInt(MIN_FOOD_QUANTITY_TO_CREATE, halfMapSize);
        EntityType foodType = getFoodType(hungryCreature);
        addEntitiesToVoidCells(foodType, foodQuantity, map);
    }

    private EntityType getFoodType(EntityType creatureType) {
        return switch (creatureType) {
            case HERBIVORE -> EntityType.GRASS;
            case PREDATOR -> EntityType.HERBIVORE;
            default -> throw new IllegalArgumentException("Unexpected creature type: " + creatureType);
        };
    }

    private void addEntitiesToVoidCells(EntityType entityTypeToCreate, int quantityToCreate, EntityMap map) {
        for (int i = 0; i < quantityToCreate; i++) {
            List<Coordinates> voidCells = EntityMapUtils.getVoidCells(map);
            Coordinates voidCell = voidCells.get(RANDOM.nextInt(voidCells.size()));
            map.add(voidCell, getEntityFromType(entityTypeToCreate, voidCell));
        }
    }

    private boolean hasNoPredators(EntityMap map) {
        return map.values().stream().filter(Objects::nonNull).noneMatch(e -> e.getType().equals(EntityType.PREDATOR));
    }

    private void addPredators(EntityMap map) {
        int halfMapSize = (map.getWidth()+ map.getHeight()) / 2;
        int predatorsQuantity = RANDOM.nextInt(MIN_PREDATORS_QUANTITY_TO_CREATE, halfMapSize);
        addEntitiesToVoidCells(EntityType.PREDATOR, predatorsQuantity, map);
    }

    private static boolean hasNoFood(Map.Entry<EntityType, Boolean> entryWithCreatureAndFood) {
        return !entryWithCreatureAndFood.getValue();
    }

    private void increaseCounter(EntityType entityType) {
        switch (entityType) {
            case HERBIVORE:
                withoutHerbivoreFoodCounter++;
                break;
            case PREDATOR:
                withoutPredatorFoodCounter++;
                break;
            default:
                throw new IllegalArgumentException("unexpected entityType: " + entityType);
        }
    }
}

