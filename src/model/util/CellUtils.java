package model.util;

import model.entitymap.Coordinates;
import model.entities.Entity;
import model.entitymap.EntityMap;
import model.entities.EntityType;

public final class CellUtils {
    private CellUtils() {
    }

    public static boolean isCellVoid(Coordinates cell, EntityMap entityMap) {
        return entityMap.get(cell) == null;
    }

    public static boolean isCellTarget(Coordinates cell, EntityType target, EntityMap entityMap) {
        if (isCellVoid(cell, entityMap)) {
            return false;
        }
        return entityMap.get(cell).getType().equals(target);
    }

    public static boolean isCellGrass(Coordinates nextCell, EntityMap entityMap) {
        Entity entity = entityMap.get(nextCell);
        if (entity == null) {
            return false;
        }
        return entity.getType().equals(EntityType.GRASS);
    }

    public static boolean isNeighbours(Coordinates nextCell, Coordinates targetCell) {
        int dx = Math.abs(nextCell.getX() - targetCell.getX());
        int dy = Math.abs(nextCell.getY() - targetCell.getY());
        return dx + dy == 1;
    }

}
