public class Predator extends Creature{
    protected Predator(int coordinateX, int coordinateY,int healthPoints, int speed) {
        super(coordinateX,coordinateY,healthPoints, speed, Food.HERBIVORE);
    }

    @Override
    public void makeMove() {

    }
    @Override
    public void eat() {

    }
}
