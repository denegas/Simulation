package model.service;

import model.entities.creatures.Creature;
import model.util.CreatureUtils;
import resources.SimulationConfig;

public final class HungryService {


    private HungryService(){}

    public static void apply(Creature creature) {
        if (creature.getTurnsWithoutFood() > SimulationConfig.CREATURE_MAX_TURNS_WITHOUT_FOOD_BEFORE_HUNGER) {
            hungerEffect(creature);
        }
    }
    public static void addHungryTurn(Creature creature) {
        creature.setTurnsWithoutFood(creature.getTurnsWithoutFood() + 1);
    }

    private static void hungerEffect(Creature creature) {
        if (CreatureUtils.isPredator(creature)) {
            hungerLowsPredatorSpeed(creature);
        }
        hungerLowsCreatureHP(creature);
    }

    private static void hungerLowsPredatorSpeed(Creature predator) {
        predator.setSpeed(SimulationConfig.PREDATOR_LOW_SPEED);
    }

    private static void hungerLowsCreatureHP(Creature creature) {
        if (creature.getHealthPoints() < 1) {
            creature.kill();
            return;
        }
        creature.setHealthPoints(creature.getHealthPoints() - SimulationConfig.HUNGER_HP_LOSS);
    }
}
