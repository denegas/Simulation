package Model.utils;

import Model.Coordinates;
import Model.EntityMap;
import Model.EntityType;

public final class CellUtils {
    private CellUtils(){}

    public static boolean isCellVoid(Coordinates cell, EntityMap map) {
        return map.get(cell) == null;
    }
    public static boolean isCellTarget(Coordinates cell, EntityType target, EntityMap map) {
        if (map.get(cell) == null) return false;
        return map.get(cell).getType().equals(target);
    }
    public static boolean isNeighbours(Coordinates nextCell, Coordinates targetCell) {
        int dx = Math.abs(nextCell.getCoordinateX() - targetCell.getCoordinateX());
        int dy = Math.abs(nextCell.getCoordinateY() - targetCell.getCoordinateY());
        return dx + dy == 1;
    }

}
