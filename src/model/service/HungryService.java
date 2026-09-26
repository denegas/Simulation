package model.service;

import model.entities.creatures.Creature;
import model.util.CreatureUtils;

public final class HungryService {

    private final int turnsBeforeHunger;
    private final int hungerHpLoss;
    private final int predatorLowSpeed;

    public HungryService(int turnsBeforeHunger, int hungerHpLoss, int predatorLowSpeed){
        this.hungerHpLoss = hungerHpLoss;
        this.turnsBeforeHunger = turnsBeforeHunger;
        this.predatorLowSpeed = predatorLowSpeed;
    }

    public void apply(Creature creature) {
        if (creature.getTurnsWithoutFood() > turnsBeforeHunger) {
            hungerEffect(creature);
        }
    }
    public void addHungryTurn(Creature creature) {
        creature.setTurnsWithoutFood(creature.getTurnsWithoutFood() + 1);
    }

    private void hungerEffect(Creature creature) {
        if (CreatureUtils.isPredator(creature)) {
            hungerLowsPredatorSpeed(creature);
        }
        hungerLowsCreatureHP(creature);
    }

    private void hungerLowsPredatorSpeed(Creature predator) {
        predator.setSpeed(predatorLowSpeed);
    }

    private void hungerLowsCreatureHP(Creature creature) {
        if (creature.getHealthPoints() < 1) {
            creature.kill();
            return;
        }
        creature.setHealthPoints(creature.getHealthPoints() - hungerHpLoss);
    }
}
