package Controller;

import Model.*;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class EntityCreator {

    protected final NavigableMap<Double, EntityType> entityChances = new TreeMap<>();

    protected static final double HERBIVORE_SPAWN_CHANCE_PER_ONE_CELL = 0.14;
    protected static final double PREDATOR_SPAWN_CHANCE_PER_ONE_CELL = 0.1;
    protected static final double GRASS_SPAWN_CHANCE_PER_ONE_CELL = 0.09;
    protected static final double ROCK_SPAWN_CHANCE_PER_ONE_CELL = 0.03;
    protected static final double TREE_SPAWN_CHANCE_PER_ONE_CELL = 0.05;
    protected static final Random random = new Random();

    public EntityCreator(){
        initEntityChances();
    }

    protected void initEntityChances() {
        entityChances.put(HERBIVORE_SPAWN_CHANCE_PER_ONE_CELL, EntityType.HERBIVORE);
        entityChances.put(PREDATOR_SPAWN_CHANCE_PER_ONE_CELL, EntityType.PREDATOR);
        entityChances.put(GRASS_SPAWN_CHANCE_PER_ONE_CELL, EntityType.GRASS);
        entityChances.put(ROCK_SPAWN_CHANCE_PER_ONE_CELL, EntityType.ROCK);
        entityChances.put(TREE_SPAWN_CHANCE_PER_ONE_CELL, EntityType.TREE);
    }

    protected Entity getEntityFromType(EntityType type, Coordinates cell) {
        return switch (type) {
            case EntityType.HERBIVORE -> new Herbivore(cell, Herbivore.MAX_HEALTH_POINTS, Herbivore.SPEED);
            case EntityType.PREDATOR -> new Predator(cell, Predator.MAX_HEALTH_POINTS, Predator.MAX_SPEED);
            case EntityType.GRASS -> new Grass(cell);
            case EntityType.ROCK -> new Rock(cell);
            case EntityType.TREE -> new Tree(cell);
            default -> throw new IllegalArgumentException("Unexpected entity type: " + type);
        };
    }
}
