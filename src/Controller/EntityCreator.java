package Controller;

import Model.*;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class EntityCreator {
    public EntityCreator(){
        initEntityChances();
    }
    protected final NavigableMap<Double, EntityType> entityChances = new TreeMap<>();
    protected static final int PREDATOR_SPEED = 2;
    protected static final int HERBIVORE_SPEED = 1;
    protected static final int PREDATOR_HP = 1;
    protected static final int HERBIVORE_HP = 1;

    // it's spawn entities spawn chance PER 1 CELL in map, not for all map
    protected static final double HERBIVORE_SPAWN_CHANCE = 0.14;
    protected static final double PREDATOR_SPAWN_CHANCE = 0.1;
    protected static final double GRASS_SPAWN_CHANCE = 0.09;
    protected static final double ROCK_SPAWN_CHANCE = 0.03;
    protected static final double TREE_SPAWN_CHANCE = 0.05;
    protected static final Random random = new Random();

    protected void initEntityChances() {
        entityChances.put(HERBIVORE_SPAWN_CHANCE, EntityType.HERBIVORE);
        entityChances.put(PREDATOR_SPAWN_CHANCE, EntityType.PREDATOR);
        entityChances.put(GRASS_SPAWN_CHANCE, EntityType.GRASS);
        entityChances.put(ROCK_SPAWN_CHANCE, EntityType.ROCK);
        entityChances.put(TREE_SPAWN_CHANCE, EntityType.TREE);
    }

    protected Entity getEntityFromType(EntityType type, Coordinates cell) {
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
