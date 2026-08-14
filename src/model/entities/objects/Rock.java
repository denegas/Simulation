package model.entities.objects;

import model.entitymap.Coordinates;
import model.entities.Entity;
import model.entities.EntityType;

public class Rock extends Entity {
    public Rock(Coordinates coordinates) {
        super(coordinates, EntityType.ROCK);
    }
}
