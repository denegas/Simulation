package model.entities.creatures;

import model.entities.Entity;
import model.entitymap.Coordinates;

public class Predator extends Creature {

    public static final Class<? extends Entity> TARGET = Herbivore.class;
    public static final int MAX_HEALTH_POINTS = 10;
    public static final int MAX_SPEED = 2;
    public static final int LOW_SPEED = 1;
    public static final double ATTACK_CHANCE = 0.9;
    public static final int ATTACK_POWER = 3;

    public Predator(Coordinates coordinates, int healthPoints, int speed) {
        super(coordinates, healthPoints, speed);
    }

    @Override
    public void restoreHealthPoints() {
        setHealthPoints(MAX_HEALTH_POINTS);
    }

    @Override
    public Class<? extends Entity> getTarget() {
        return TARGET;
    }
}
