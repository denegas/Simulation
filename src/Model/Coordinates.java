package Model;

import java.util.Comparator;

public class Coordinates implements Comparable<Coordinates> {
    public Coordinates(int coordinateX,int coordinateY){
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }
    private final int coordinateX;
    private final int coordinateY;
    public int getCoordinateX(){
        return coordinateX;
    }
    public int getCoordinateY(){
        return coordinateY;
    }

    @Override
    public int compareTo(Coordinates coordinates) {
        return Comparator.comparingInt(Coordinates::getCoordinateX).thenComparingInt(Coordinates::getCoordinateY).compare(this,coordinates);
    }

    @Override
    public String toString() {
        return "X: " + getCoordinateX() + " Y: " + getCoordinateY();
    }
}
