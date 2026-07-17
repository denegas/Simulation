package Model.entities.creatures;

import Model.map.Coordinates;
import Model.entities.EntityType;

public class Herbivore extends Creature {

    public static final int MAX_HEALTH_POINTS = 15;
    public static final int SPEED = 1;

    public Herbivore(Coordinates coordinates, int healthPoints, int speed) {
        super(coordinates, EntityType.HERBIVORE, healthPoints, speed);
    }

    @Override
    public void restoreHealthPoints() {
        setHealthPoints(MAX_HEALTH_POINTS);
    }
}
