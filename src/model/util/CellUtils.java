package model.util;

import model.entities.environment.Grass;
import model.entitymap.Coordinates;
import model.entities.Entity;
import model.entitymap.EntityMap;

public final class CellUtils {
    private CellUtils() {
    }

    public static boolean isCellVoid(Coordinates cell, EntityMap entityMap) {
        return entityMap.get(cell) == null;
    }

    public static boolean isCellTarget(Coordinates cell, Class<? extends Entity> target, EntityMap entityMap) {
        if (isCellVoid(cell, entityMap)) {
            return false;
        }

        return target.isInstance(entityMap.get(cell));
    }

    public static boolean isCellGrass(Coordinates nextCell, EntityMap entityMap) {
        Entity entity = entityMap.get(nextCell);
        if (entity == null) {
            return false;
        }
        return entity.getClass() == Grass.class;
    }

    public static boolean isNeighbours(Coordinates nextCell, Coordinates targetCell) {
        int dx = Math.abs(nextCell.getX() - targetCell.getX());
        int dy = Math.abs(nextCell.getY() - targetCell.getY());
        return dx + dy == 1;
    }

}
