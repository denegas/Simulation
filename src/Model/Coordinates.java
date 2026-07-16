package Model;

import java.util.Comparator;

public class Coordinates implements Comparable<Coordinates> {

    private final int coordinateX;
    private final int coordinateY;

    public Coordinates(int coordinateX, int coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    @Override
    public int compareTo(Coordinates coordinates) {
        return Comparator.comparingInt(Coordinates::getCoordinateX).thenComparingInt(Coordinates::getCoordinateY).compare(this, coordinates);
    }

    @Override
    public String toString() {
        return "X: " + getCoordinateX() + " Y: " + getCoordinateY();
    }

    @Override
    public boolean equals(Object o) {
        Coordinates cord1 = (Coordinates) o;
        return (this.getCoordinateX() == cord1.getCoordinateX()) && (this.getCoordinateY() == cord1.getCoordinateY());
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(coordinateX);
        result = 31 * result + Integer.hashCode(coordinateY);
        return result;
    }
}
