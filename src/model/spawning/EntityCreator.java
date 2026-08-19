package model.spawning;

import model.entities.Entity;
import model.entities.creatures.Herbivore;
import model.entities.creatures.Predator;
import model.entities.environment.Grass;
import model.entities.environment.Rock;
import model.entities.environment.Tree;
import model.entitymap.Coordinates;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public abstract class EntityCreator {

    protected static final Random RANDOM = new Random();
    protected static final double HERBIVORE_SPAWN_CHANCE_PER_ONE_CELL = 0.14;
    protected static final double PREDATOR_SPAWN_CHANCE_PER_ONE_CELL = 0.1;
    protected static final double GRASS_SPAWN_CHANCE_PER_ONE_CELL = 0.09;
    protected static final double TREE_SPAWN_CHANCE_PER_ONE_CELL = 0.05;
    protected static final double ROCK_SPAWN_CHANCE_PER_ONE_CELL = 0.03;

    protected static final List<EntitySpawnChance> spawnChances = Stream.of(
            new EntitySpawnChance(Herbivore.class, HERBIVORE_SPAWN_CHANCE_PER_ONE_CELL),
            new EntitySpawnChance(Predator.class, PREDATOR_SPAWN_CHANCE_PER_ONE_CELL),
            new EntitySpawnChance(Grass.class, GRASS_SPAWN_CHANCE_PER_ONE_CELL),
            new EntitySpawnChance(Rock.class, ROCK_SPAWN_CHANCE_PER_ONE_CELL),
            new EntitySpawnChance(Tree.class, TREE_SPAWN_CHANCE_PER_ONE_CELL)
    )
            .sorted(Comparator.comparingDouble(EntitySpawnChance::chance))
            .toList();

    protected Entity getEntityFromClass(Class<? extends Entity> entityClass, Coordinates coordinates) {
        if (entityClass == Herbivore.class) {
            return new Herbivore(coordinates, Herbivore.MAX_HEALTH_POINTS, Herbivore.SPEED);
        }
        if (entityClass == Predator.class) {
            return new Predator(coordinates, Predator.MAX_HEALTH_POINTS, Predator.MAX_SPEED);
        }
        if (entityClass == Grass.class) {
            return new Grass();
        }
        if (entityClass == Rock.class) {
            return new Rock();
        }
        if (entityClass == Tree.class) {
            return new Tree();
        }

        throw new IllegalArgumentException("Unexpected entity class: " + entityClass);
    }
}
