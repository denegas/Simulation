package Model;

public class Predator extends Creature{
    public Predator(Coordinates coordinates,int healthPoints, int speed) {
        super(coordinates,EntityType.PREDATOR,healthPoints, speed, Food.HERBIVORE);
    }
    public static final int MAX_HEALTH_POINTS = 10;
    public static final double ATTACK_CHANCE = 0.9;
    public static final int ATTACK_POWER = 3;
    @Override
    public void restoreHealthPoints() {
        setHealthPoints(MAX_HEALTH_POINTS);
    }

    //    @Override
//    public void makeMove(Coordinates newCoordinates) {
//        setCoordinates(newCoordinates);
//    }
    @Override
    public void eat() {

    }
}
