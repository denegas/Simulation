package Model;

public class Predator extends Creature{
    public Predator(Coordinates coordinates,int healthPoints, int speed) {
        super(coordinates,EntityType.PREDATOR,healthPoints, speed, Food.HERBIVORE);
    }
    private final int MAX_HEALTH_POINTS = 10;
    private static final double ATTACK_CHANCE = 0.75;
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
    public static double getATTACK_CHANCE(){
        return ATTACK_CHANCE;
    }
}
