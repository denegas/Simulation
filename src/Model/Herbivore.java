package Model;

public class Herbivore extends Creature {

    public static final int MAX_HEALTH_POINTS = 15;
    public static final int SPEED = 1;

    public Herbivore(Coordinates coordinates, int healthPoints, int speed) {
        super(coordinates, EntityType.HERBIVORE, healthPoints, speed, Food.GRASS);
    }

    @Override
    public void restoreHealthPoints() {
        setHealthPoints(MAX_HEALTH_POINTS);
    }
}
