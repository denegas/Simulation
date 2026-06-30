package Model;

public class Herbivore extends Creature{
    public Herbivore(Coordinates coordinates,int healthPoints,int speed){
        super(coordinates,EntityType.HERBIVORE,healthPoints,speed,Food.GRASS);
    }

    @Override
    public void makeMove(Coordinates newCoordinates) {
          setCoordinates(newCoordinates);
    }
    @Override
    public void eat() {

    }


}
