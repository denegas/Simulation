package model.entities.creatures;

import model.entities.Entity;
import model.entities.environment.Grass;
import model.entitymap.Coordinates;

public class Herbivore extends Creature {

    public static final int MAX_HEALTH_POINTS = 15;
    public static final int SPEED = 1;
    public static final Class<Grass> TARGET = Grass.class;

    public Herbivore(Coordinates coordinates, int healthPoints, int speed) {
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
