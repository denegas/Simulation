package Model.utils;

import Model.Creature;
import Model.EntityMap;

import java.util.List;

public final class MapUtils {

    private MapUtils(){}

    public static List<Creature> getCreatures(EntityMap map) {
        return map.getCellsWithCreatures().values().stream().toList();
    }

    public static void cleanMapFromDeadCreatures(EntityMap map) {
        for (Creature creature : getCreatures(map)) {
            if (creature.isDead()) {
                map.removeEntity(creature.getCoordinates());
            }
        }
    }
}
