package model.util;

import model.entities.Entity;
import model.entities.creatures.Creature;
import model.entitymap.Coordinates;
import model.entitymap.EntityMap;

import java.util.List;
import java.util.stream.Collectors;

public final class EntityMapUtils {

    private EntityMapUtils() {
    }

    public static <T extends Entity> List<T> getEntitiesBy(Class<T> entityClass, EntityMap map) {
        return map.entrySet()
                .stream()
                .filter(entry -> {
                    if (entry.getValue() == null) {
                        return false;
                    }

                    return entityClass.isAssignableFrom(entry.getValue().getClass());
                })
                .map(entry -> entityClass.cast(entry.getValue()))
                .collect(Collectors.toList());
    }

    public static void cleanMapFromDeadCreatures(EntityMap entityMap) {
        for (Creature creature : getEntitiesBy(Creature.class, entityMap)) {
            if (!creature.isAlive()) {
                entityMap.clearCell(creature.getCoordinates());
            }
        }
    }

    public static boolean hasMapCell(Coordinates coordinates, EntityMap entityMap) {
        int borderX = entityMap.getWidth();
        int borderY = entityMap.getHeight();
        int x = coordinates.getX();
        int y = coordinates.getY();
        if (x < 0 || y < 0) {
            return false;
        }
        return (x < borderX) && (y < borderY);
    }

    public static List<Coordinates> getVoidCells(EntityMap entityMap) {
        return entityMap.entrySet()
                .stream()
                .filter(e -> e.getValue() == null)
                .map(e -> e.getKey())
                .toList();
    }
}
