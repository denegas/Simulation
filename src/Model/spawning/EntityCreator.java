package Model.spawning;

import Model.entities.Entity;
import Model.entities.EntityType;
import Model.entities.creatures.Herbivore;
import Model.entities.creatures.Predator;
import Model.entities.objects.Grass;
import Model.entities.objects.Rock;
import Model.entities.objects.Tree;
import Model.map.Coordinates;

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
            new EntitySpawnChance(EntityType.HERBIVORE, HERBIVORE_SPAWN_CHANCE_PER_ONE_CELL),
            new EntitySpawnChance(EntityType.PREDATOR, PREDATOR_SPAWN_CHANCE_PER_ONE_CELL),
            new EntitySpawnChance(EntityType.GRASS, GRASS_SPAWN_CHANCE_PER_ONE_CELL),
            new EntitySpawnChance(EntityType.ROCK, ROCK_SPAWN_CHANCE_PER_ONE_CELL),
            new EntitySpawnChance(EntityType.TREE, TREE_SPAWN_CHANCE_PER_ONE_CELL)
    )
            .sorted(Comparator.comparingDouble(EntitySpawnChance::chance))
            .toList();

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
