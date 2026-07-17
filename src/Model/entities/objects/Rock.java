package Model.entities.objects;

import Model.map.Coordinates;
import Model.entities.Entity;
import Model.entities.EntityType;

public class Rock extends Entity {
    public Rock(Coordinates coordinates) {
        super(coordinates, EntityType.ROCK);
    }
}
