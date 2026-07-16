package Model.services;

import Model.Creature;
import Model.Predator;
import Model.utils.CreatureUtils;

public final class HungryService {
    private static final int HUNGER_HP_LOSS = 1;

    private HungryService(){}

    public static void apply(Creature creature) {
        if (creature.getTurnsWithoutFood() > Creature.MAX_TURNS_WITHOUT_FOOD) {
            hungerEffect(creature);
        }
    }

    private static void hungerEffect(Creature creature) {
        if (CreatureUtils.isPredator(creature)) {
            hungerLowsPredatorSpeed(creature);
        }
        hungerLowsCreatureHP(creature);
    }

    private static void hungerLowsPredatorSpeed(Creature predator) {
        predator.setSpeed(Predator.LOW_SPEED);
    }

    private static void hungerLowsCreatureHP(Creature creature) {
        if (creature.getHealthPoints() < 1) {
            creature.kill();
            return;
        }
        creature.setHealthPoints(creature.getHealthPoints() - HUNGER_HP_LOSS);
    }
}
