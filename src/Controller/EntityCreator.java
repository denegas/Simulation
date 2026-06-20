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
        if (!isAtLeastOneHerbivore()){
            addOneHerbivore();
        }
        if(!isAtLeastOnePredator()){
            addOnePredator();
        }
        Simulation.setMap(EntityMap.getMap());
    }

    private static boolean isAtLeastOnePredator() {
            for (Entity entity: EntityMap.getMap().values().stream().filter(Objects::nonNull).toList()){
                if (entity.getType() == EntityType.PREDATOR) return true;
            }
            return false;
    }
    private static boolean isAtLeastOneHerbivore() {
        for (Entity entity: EntityMap.getMap().values().stream().filter(Objects::nonNull).toList()){
            if (entity.getType() == EntityType.HERBIVORE) return true;
        }
        return false;
    }
    private  void addOnePredator(){
        List<Coordinates> voidFields = EntityMap.getMap()
                .entrySet()
                .stream()
                .filter(e->e.getValue() == null)
                .map(e ->e.getKey())
                .toList();
        Coordinates randomCoordinates = voidFields.get(random.nextInt(voidFields.size()));
        EntityMap.add(randomCoordinates,new Predator(randomCoordinates, PREDATOR_HP, PREDATOR_SPEED));
    }
    private  void addOneHerbivore(){
        List<Coordinates> voidFields = EntityMap.getMap()
                .entrySet()
                .stream()
                .filter(e->e.getValue() == null)
                .map(e ->e.getKey())
                .toList();
        Coordinates randomCoordinates = voidFields.get(random.nextInt(voidFields.size()));
        EntityMap.add(randomCoordinates,new Herbivore(randomCoordinates, HERBIVORE_HP, HERBIVORE_SPEED));
    }

}
