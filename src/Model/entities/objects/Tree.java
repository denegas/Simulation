package Model.entities.objects;

import Model.map.Coordinates;
import Model.entities.Entity;
import Model.entities.EntityType;

public class Tree extends Entity {
    public Tree(Coordinates coordinates) {
        super(coordinates, EntityType.TREE);
    }
}
