package Model.util;

import Model.map.Coordinates;
import Model.entities.Entity;
import Model.map.EntityMap;
import Model.entities.EntityType;

public final class CellUtils {
    private CellUtils() {
    }

    public static boolean isCellVoid(Coordinates cell, EntityMap map) {
        return map.get(cell) == null;
    }

    public static boolean isCellTarget(Coordinates cell, EntityType target, EntityMap map) {
        if (isCellVoid(cell, map)) {
            return false;
        }
        return map.get(cell).getType().equals(target);
    }

    public static boolean isCellGrass(Coordinates nextCell, EntityMap map) {
        Entity entity = map.get(nextCell);
        if (entity == null) {
            return false;
        }
        return entity.getType().equals(EntityType.GRASS);
    }

    public static boolean isNeighbours(Coordinates nextCell, Coordinates targetCell) {
        int dx = Math.abs(nextCell.getCoordinateX() - targetCell.getCoordinateX());
        int dy = Math.abs(nextCell.getCoordinateY() - targetCell.getCoordinateY());
        return dx + dy == 1;
    }

    public static boolean isSameCreaturesOnCells(Coordinates cellOne, Coordinates cellTwo, EntityMap map) {
        if (CellUtils.isCellVoid(cellOne, map) || CellUtils.isCellVoid(cellTwo, map)) {
            return false;
        }
        EntityType firstCreatureType = map.get(cellOne).getType();
        EntityType secondCreatureType = map.get(cellTwo).getType();

        return firstCreatureType.equals(secondCreatureType);
    }

}
