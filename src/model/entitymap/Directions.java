package model.entitymap;

public final class Directions {
    public static final Coordinates[] NEAR_DIRECTIONS = {
            new Coordinates(0,1),
            new Coordinates(0, -1),
            new Coordinates(1, 0),
            new Coordinates(-1, 0)
    };

    private Directions() {
    }
}
