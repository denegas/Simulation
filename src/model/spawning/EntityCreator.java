package model.spawning;

import model.entities.Entity;
import model.entities.creatures.Herbivore;
import model.entities.creatures.Predator;
import model.entities.environment.Grass;
import model.entities.environment.Rock;
import model.entities.environment.Tree;
import model.entitymap.Coordinates;
import resources.SimulationConfig;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public abstract class EntityCreator {

    protected static final Random RANDOM = new Random();
    protected static final List<EntitySpawnChance> spawnChances = Stream.of(
            new EntitySpawnChance(Herbivore.class, SimulationConfig.HERBIVORE_SPAWN_CHANCE_PER_ONE_CELL),
            new EntitySpawnChance(Predator.class, SimulationConfig.PREDATOR_SPAWN_CHANCE_PER_ONE_CELL),
            new EntitySpawnChance(Grass.class, SimulationConfig.GRASS_SPAWN_CHANCE_PER_ONE_CELL),
            new EntitySpawnChance(Rock.class, SimulationConfig.ROCK_SPAWN_CHANCE_PER_ONE_CELL),
            new EntitySpawnChance(Tree.class, SimulationConfig.TREE_SPAWN_CHANCE_PER_ONE_CELL)
    )
            .sorted(Comparator.comparingDouble(EntitySpawnChance::chance))
            .toList();

    protected Entity getEntityFromClass(Class<? extends Entity> entityClass, Coordinates coordinates) {
        if (entityClass.isAssignableFrom(Herbivore.class)) {
            return new Herbivore(coordinates, Herbivore.MAX_HEALTH_POINTS, Herbivore.SPEED);
        }
        if (entityClass.isAssignableFrom(Predator.class)) {
            return new Predator(coordinates, Predator.MAX_HEALTH_POINTS, Predator.MAX_SPEED);
        }
        if (entityClass.isAssignableFrom(Grass.class)) {
            return new Grass();
        }
        if (entityClass.isAssignableFrom(Rock.class)) {
            return new Rock();
        }
        if (entityClass.isAssignableFrom(Tree.class)) {
            return new Tree();
        }

        throw new IllegalArgumentException("Unexpected entity class: " + entityClass);
    }
}
