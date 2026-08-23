package model.util;

import model.entities.environment.Grass;
import model.entitymap.Coordinates;
import model.entities.Entity;
import model.entitymap.EntityMap;

import java.util.Optional;

public final class CellUtils {
    private CellUtils() {
    }

    public static boolean isCellVoid(Coordinates cell, EntityMap entityMap) {
        return entityMap.get(cell).isEmpty();
    }

    public static boolean isCellTarget(Coordinates cell, Class<? extends Entity> target, EntityMap entityMap) {
        if (isCellVoid(cell, entityMap)) {
            return false;
        }

        return target.isInstance(entityMap.get(cell).orElseThrow());
    }

    public static boolean isCellGrass(Coordinates nextCell, EntityMap entityMap) {
        Optional<Entity> entity = entityMap.get(nextCell);
        if (entity.isEmpty()) {

            return false;
        }
        return entity.get().getClass() == Grass.class;
    }

    public static boolean isNeighbours(Coordinates nextCell, Coordinates targetCell) {
        int dx = Math.abs(nextCell.x() - targetCell.x());
        int dy = Math.abs(nextCell.y() - targetCell.y());
        return dx + dy == 1;
    }

}
