package Model;

public class Coordinates {
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
}
