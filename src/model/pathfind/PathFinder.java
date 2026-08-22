package model.pathfind;

import model.entities.Entity;
import model.entitymap.Coordinates;
import model.entitymap.EntityMap;

import java.util.List;

public interface PathFinder {
    List<Coordinates> getPath(EntityMap map, Coordinates startPosition, Class<? extends Entity> target);
}
