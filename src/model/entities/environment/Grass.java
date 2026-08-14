package model.entities.environment;

import model.entitymap.Coordinates;
import model.entities.Entity;
import model.entities.EntityType;

public class Grass extends Entity {
    public Grass(Coordinates coordinates) {
        super(coordinates, EntityType.GRASS);
    }
}
