package model.actions.turnActions;

import model.entities.Entity;
import model.entities.creatures.Creature;
import model.entities.creatures.Herbivore;
import model.entities.creatures.Predator;
import model.entities.environment.Grass;
import model.entitymap.Coordinates;
import model.entitymap.EntityMap;
import model.actions.Action;
import model.spawning.EntityCreator;
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
        Map<Class<? extends Creature>, Boolean> hasFoodForCreature = getEntityTypeBooleanMap(map);

        for (var entry : hasFoodForCreature.entrySet()) {

            if (withoutPredatorFoodCounter > MAX_TURNS_WITHOUT_FOOD) {
                addMissingFoodFor(Predator.class, map);
                withoutPredatorFoodCounter = 0;
            }
            if (withoutHerbivoreFoodCounter > MAX_TURNS_WITHOUT_FOOD) {
                addMissingFoodFor(Herbivore.class, map);
                withoutHerbivoreFoodCounter = 0;
            }
            if (hasNoEntitiesBy(Predator.class, map)) {
                addPredators(map);
            }
            if (hasNoFood(entry)) {
                increaseCounter(entry.getKey());
            }
        }
    }

    private static Map<Class<? extends Creature>, Boolean> getEntityTypeBooleanMap(EntityMap entityMap) {
        boolean hasPredatorFood = false;
        boolean hasHerbivoreFood = false;

        for (var entity : EntityMapUtils.getEntitiesBy(Entity.class, entityMap)) {
            if (entity instanceof Herbivore){
                hasPredatorFood = true;

            } else if (entity instanceof Grass){
                hasHerbivoreFood = true;
            }

        }
        return Map.of(Herbivore.class, hasHerbivoreFood, Predator.class, hasPredatorFood);
    }

    private void addMissingFoodFor(Class<? extends Creature> hungryCreature, EntityMap map) {
        int halfMapSize = (map.getWidth()+ map.getHeight()) / 2;
        int foodQuantity = RANDOM.nextInt(MIN_FOOD_QUANTITY_TO_CREATE, halfMapSize);
        Class<? extends Entity> foodType = getFoodType(hungryCreature);
        addEntitiesToVoidCells(foodType, foodQuantity, map);
    }

    private Class<? extends Entity> getFoodType(Class<? extends Creature> creatureClass) {

        if (Herbivore.class.isAssignableFrom(creatureClass)){
            return Grass.class;
        }
        if (Predator.class.isAssignableFrom(creatureClass)){
            return Herbivore.class;
        }
        throw new IllegalArgumentException("Unexpected creature class: " + creatureClass);
    }

    private void addEntitiesToVoidCells(Class<? extends Entity> entityToCreate, int quantityToCreate, EntityMap map) {
        for (int i = 0; i < quantityToCreate; i++) {
            List<Coordinates> voidCells = EntityMapUtils.getVoidCells(map);
            Coordinates voidCell = voidCells.get(RANDOM.nextInt(voidCells.size()));
            map.add(voidCell, getEntityFromClass(entityToCreate, voidCell));
        }
    }

    private boolean hasNoEntitiesBy(Class<? extends Entity> entityClass, EntityMap entityMap) {
        return entityMap.values()
                .stream()
                .filter(Objects::nonNull)
                .noneMatch(e -> entityClass.isInstance(e));
    }

    private void addPredators(EntityMap entityMap) {
        int halfMapSize = (entityMap.getWidth()+ entityMap.getHeight()) / 2;
        int predatorsQuantity = RANDOM.nextInt(MIN_PREDATORS_QUANTITY_TO_CREATE, halfMapSize);
        addEntitiesToVoidCells(Predator.class, predatorsQuantity, entityMap);
    }

    private static boolean hasNoFood(Map.Entry<Class<? extends Creature>, Boolean> entryWithCreatureAndFood) {
        return !entryWithCreatureAndFood.getValue();
    }

    private void increaseCounter(Class<? extends Creature> entityClass) {

        if (entityClass.isAssignableFrom(Herbivore.class)){
            withoutHerbivoreFoodCounter++;
        } else if (entityClass.isAssignableFrom(Predator.class)){
            withoutPredatorFoodCounter++;
        } else{
            throw new IllegalArgumentException("Unexpected entityClass: " + entityClass);
        }
    }
}

