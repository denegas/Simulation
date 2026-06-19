public class Herbivore extends Creature{
    public Herbivore(int coordinateX, int coordinateY,int healthPoints,int speed){
        super(coordinateX,coordinateY,healthPoints,speed,Food.GRASS);
    }
    @Override
    public void makeMove() {

    }
    @Override
    public void eat() {

    }

}
