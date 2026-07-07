package Controller;

import Model.*;
import java.util.*;

public final class EntityCreator implements Action {
    public EntityCreator() {

    }
    private final NavigableMap<Double, EntityType> entityChances = new TreeMap<>();
    private static final int PREDATOR_SPEED = 2;
    private static final int HERBIVORE_SPEED = 1;
    private static final int PREDATOR_HP = 1;
    private static final int HERBIVORE_HP = 1;

    // it's spawn entities spawn chance PER 1 CELL in map, not for all map
    private static final double HERBIVORE_SPAWN_CHANCE = 0.14;
    private static final double PREDATOR_SPAWN_CHANCE = 0.1;
    private static final double GRASS_SPAWN_CHANCE = 0.09;
    private static final double ROCK_SPAWN_CHANCE = 0.03;
    private static final double TREE_SPAWN_CHANCE = 0.05;
    private static final Random random = new Random();

    @Override
    public void execute(EntityMap map) {
        initEntityChances();
        for (var cell : map.getMap().keySet()) { // get all cells with coordinates from map
            double randomChance = random.nextDouble();
            var entry = entityChances.ceilingEntry(randomChance);
            if (entry != null) {
                EntityType type = entry.getValue();
                map.add(cell, getEntityFromType(type, cell));
            }
        }
        if (hasNoEntity(EntityType.HERBIVORE,map)) {
            addOneEntity(EntityType.HERBIVORE,map);
        }
        if (hasNoEntity(EntityType.PREDATOR,map)) {
            addOneEntity(EntityType.PREDATOR,map);
        }
        Simulation.setMap(map);
    }

    private boolean hasNoEntity(EntityType entityType, EntityMap map) {
        for (Entity entity : map.getNotNullEntities()) {
            if (entity.getType() == entityType) return false;
        }
        return true;
    }
    // add 1 entity to a random void cell in map
    private void addOneEntity(EntityType entityType, EntityMap map) {

        List<Coordinates> voidCells = map.getVoidCells();

        Coordinates randomCoordinates = voidCells.get(random.nextInt(voidCells.size()));

        map.add(randomCoordinates,getEntityFromType(entityType,randomCoordinates));

    }

    private void initEntityChances() {
        entityChances.put(HERBIVORE_SPAWN_CHANCE, EntityType.HERBIVORE);
        entityChances.put(PREDATOR_SPAWN_CHANCE, EntityType.PREDATOR);
        entityChances.put(GRASS_SPAWN_CHANCE, EntityType.GRASS);
        entityChances.put(ROCK_SPAWN_CHANCE, EntityType.ROCK);
        entityChances.put(TREE_SPAWN_CHANCE, EntityType.TREE);
    }

    private Entity getEntityFromType(EntityType type, Coordinates cell) {
        return switch (type) {
            case EntityType.HERBIVORE -> new Herbivore(cell, HERBIVORE_HP, HERBIVORE_SPEED);
            case EntityType.PREDATOR -> new Predator(cell, PREDATOR_HP, PREDATOR_SPEED);
            case EntityType.GRASS -> new Grass(cell);
            case EntityType.ROCK -> new Rock(cell);
            case EntityType.TREE -> new Tree(cell);
            default -> throw new IllegalArgumentException("Unexpected entity type: " + type);
        };
    }
}
