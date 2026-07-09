package Model;

public class Herbivore extends Creature{
    public Herbivore(Coordinates coordinates,int healthPoints,int speed){
        super(coordinates,EntityType.HERBIVORE,healthPoints,speed,Food.GRASS);
    }
    private final int MAX_HEALTH_POINTS = 15;
    @Override
    public void restoreHealthPoints() {
        setHealthPoints(MAX_HEALTH_POINTS);
    }
//    @Override
//    public void makeMove(Coordinates newCoordinates) {
//          setCoordinates(newCoordinates);
//    }
    @Override
    public void eat() {

    }


}
