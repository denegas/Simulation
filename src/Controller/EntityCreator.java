package Controller;

import Model.*;
import java.util.*;

public final class EntityCreator implements Action {
    public EntityCreator(EntityMap map) {
        this.map = map;
        initEntityChances();
    }
    private final EntityMap map;
    private final NavigableMap<Double, EntityType> entityChances = new TreeMap<>();
    private static final int PREDATOR_SPEED = 2;
    private static final int HERBIVORE_SPEED = 1;
    private static final int PREDATOR_HP = 1;
    private static final int HERBIVORE_HP = 1;
    private static final double HERBIVORE_SPAWN_CHANCE = 0.14;
    private static final double PREDATOR_SPAWN_CHANCE = 0.1;
    private static final double GRASS_SPAWN_CHANCE = 0.09;
    private static final double ROCK_SPAWN_CHANCE = 0.03;
    private static final double TREE_SPAWN_CHANCE = 0.05;
    private static final Random random = new Random();

    @Override
    public void execute() {
        for (var field : Simulation.getEntityMap().getMap().keySet()) {
            double randomChance = random.nextDouble();
            var entry = entityChances.ceilingEntry(randomChance);
            if (entry != null) {
                EntityType type = entry.getValue();
                map.add(field, getEntityFromType(type, field));
            }
        }
        if (hasNoEntity(EntityType.HERBIVORE)) {
            addOneEntity(EntityType.HERBIVORE);
        }
        if (hasNoEntity(EntityType.PREDATOR)) {
            addOneEntity(EntityType.PREDATOR);
        }
        Simulation.setMap(map);
    }

    private boolean hasNoEntity(EntityType entityType) {
        for (Entity entity : map.getNotNullEntities()) {
            if (entity.getType() == entityType) return false;
        }
        return true;
    }

    private void addOneEntity(EntityType entityType) {

        List<Coordinates> voidFields = map.getVoidFields();

        Coordinates randomCoordinates = voidFields.get(random.nextInt(voidFields.size()));

        map.add(randomCoordinates,getEntityFromType(entityType,randomCoordinates));

    }

    private void initEntityChances() {
        entityChances.put(HERBIVORE_SPAWN_CHANCE, EntityType.HERBIVORE);
        entityChances.put(PREDATOR_SPAWN_CHANCE, EntityType.PREDATOR);
        entityChances.put(GRASS_SPAWN_CHANCE, EntityType.GRASS);
        entityChances.put(ROCK_SPAWN_CHANCE, EntityType.ROCK);
        entityChances.put(TREE_SPAWN_CHANCE, EntityType.TREE);
    }

    private Entity getEntityFromType(EntityType type, Coordinates field) {
        return switch (type) {
            case EntityType.HERBIVORE -> new Herbivore(field, HERBIVORE_HP, HERBIVORE_SPEED);
            case EntityType.PREDATOR -> new Predator(field, PREDATOR_HP, PREDATOR_SPEED);
            case EntityType.GRASS -> new Grass(field);
            case EntityType.ROCK -> new Rock(field);
            case EntityType.TREE -> new Tree(field);
            default -> throw new IllegalArgumentException("Unexpected entity type: " + type);
        };

    }
}
