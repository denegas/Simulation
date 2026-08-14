package model.entities.environment;

import model.entitymap.Coordinates;
import model.entities.Entity;
import model.entities.EntityType;

public class Tree extends Entity {
    public Tree(Coordinates coordinates) {
        super(coordinates, EntityType.TREE);
    }
}
