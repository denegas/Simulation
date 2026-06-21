package Controller;

import Model.*;

import java.util.*;
import java.util.stream.Collectors;

public final class EntityCreator implements Action {
    private final double HERBIVORE_CHANCE = 0.14;
    private final double PREDATOR_CHANCE = 0.1;
    private final double GRASS_CHANCE = 0.09;
    private final double ROCK_CHANCE = 0.03;
    private final double TREE_CHANCE = 0.05;
    private final int PREDATOR_SPEED = 1;
    private final int HERBIVORE_SPEED = 1;
    private final int PREDATOR_HP = 1;
    private final int HERBIVORE_HP = 1;
    private final Random random = new Random();

    @Override
    public void execute() {
        for (var field : Simulation.getMap().keySet()) {
            double randomChance = random.nextDouble();
            if (randomChance < ROCK_CHANCE) {
                EntityMap.add(field, new Rock(field));
            } else if (randomChance < TREE_CHANCE) {
                EntityMap.add(field, new Tree(field));
            } else if (randomChance < GRASS_CHANCE) {
                EntityMap.add(field, new Grass(field));
            } else if (randomChance < PREDATOR_CHANCE) {
                EntityMap.add(field, new Predator(field, PREDATOR_HP, PREDATOR_SPEED));
            } else if (randomChance < HERBIVORE_CHANCE) {
                EntityMap.add(field, new Herbivore(field, HERBIVORE_HP, HERBIVORE_SPEED));
            }

        }
        if (hasNoEntity(EntityType.HERBIVORE)){
            addOneEntity(EntityType.HERBIVORE);
        }
        if(hasNoEntity(EntityType.PREDATOR)){
            addOneEntity(EntityType.PREDATOR);
        }
        Simulation.setMap(EntityMap.getMap());
    }

    private static boolean hasNoEntity(EntityType entityType) {
            for (Entity entity: EntityMap.getMap().values().stream().filter(Objects::nonNull).toList()){
                if (entity.getType() == entityType) return false;
            }
            return true;
    }
    private void addOneEntity(EntityType entityType){
        List<Coordinates> voidFields = EntityMap.getMap()
                .entrySet()
                .stream()
                .filter(e->e.getValue() == null)
                .map(e ->e.getKey())
                .toList();
        Coordinates randomCoordinates = voidFields.get(random.nextInt(voidFields.size()));
        switch(entityType){
            case EntityType.PREDATOR:
                EntityMap.add(randomCoordinates,new Predator(randomCoordinates, PREDATOR_HP, PREDATOR_SPEED));
                break;
            case EntityType.HERBIVORE:
                EntityMap.add(randomCoordinates,new Herbivore(randomCoordinates, HERBIVORE_HP, HERBIVORE_SPEED));
                break;
            case EntityType.GRASS:
                EntityMap.add(randomCoordinates,new Grass(randomCoordinates));
        }

    }

}
