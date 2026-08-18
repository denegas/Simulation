package model.entities.creatures;

import model.entitymap.Coordinates;
import model.entities.EntityType;

public class Herbivore extends Creature {

    public static final int MAX_HEALTH_POINTS = 15;
    public static final int SPEED = 1;
    public static final EntityType TARGET = EntityType.GRASS;

    public Herbivore(Coordinates coordinates, int healthPoints, int speed) {
        super(coordinates, EntityType.HERBIVORE, healthPoints, speed);
    }

    @Override
    public void restoreHealthPoints() {
        setHealthPoints(MAX_HEALTH_POINTS);
    }

    @Override
    public EntityType getTarget() {
        return TARGET;
    }
}
