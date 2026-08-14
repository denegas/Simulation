package model.util;

import model.entities.creatures.Creature;
import model.entitymap.Coordinates;
import model.entitymap.EntityMap;
import java.util.List;

public final class MapUtils {

    private MapUtils(){}

    public static List<Creature> getCreatures(EntityMap map) {
        return map.getCellsWithCreatures().values().stream().toList();
    }

    public static void cleanMapFromDeadCreatures(EntityMap map) {
        for (Creature creature : getCreatures(map)) {
            if (creature.isDead()) {
                map.clearCell(creature.getCoordinates());
            }
        }
    }
    public static boolean hasMapCell(Coordinates coordinates, EntityMap map) {
        int border = map.size();
        int x = coordinates.getX();
        int y = coordinates.getY();
        if (x < 0 || y < 0) {
            return false;
        }
        return (x < border) && (y < border);
    }
}
