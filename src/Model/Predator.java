package Model;

public class Predator extends Creature{
    public Predator(Coordinates coordinates,int healthPoints, int speed) {
        super(coordinates,EntityType.PREDATOR,healthPoints, speed, Food.HERBIVORE);
    }

    @Override
    public void makeMove() {

    }
    @Override
    public void eat() {

    }
}
