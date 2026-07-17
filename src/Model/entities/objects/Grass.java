package Model.entities.objects;

import Model.map.Coordinates;
import Model.entities.Entity;
import Model.entities.EntityType;

public class Grass extends Entity {
    public Grass(Coordinates coordinates) {
        super(coordinates, EntityType.GRASS);
    }
}
