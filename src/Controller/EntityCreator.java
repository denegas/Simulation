package Controller;

import Model.*;

import java.util.Map;
import java.util.Random;

public final class EntityCreator implements Action {
    private final double HERBIVORE_CHANCE = 0.1;
    private final double PREDATOR_CHANCE = 0.02;
    private final double GRASS_CHANCE = 0.08;
    private final double ROCK_CHANCE = 0.00;
    private final double TREE_CHANCE = 0.00;
    private final Random random = new Random();
    @Override
    public void execute() {
        for(var field:Simulation.getMap().keySet()){
            double randomChance = random.nextDouble();
            if (randomChance <PREDATOR_CHANCE) {
                EntityMap.add(field,new Predator(field,1,1));
            }
            else if(randomChance <GRASS_CHANCE){
                EntityMap.add(field,new Grass(field));
            }
            else if(randomChance< HERBIVORE_CHANCE){
                EntityMap.add(field,new Herbivore(field,1,1));
            }

        }

    }
}
