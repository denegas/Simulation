package model.util;

import model.entities.Entity;
import model.entities.EntityType;
import model.entities.creatures.Creature;
import model.entitymap.Coordinates;
import model.entitymap.EntityMap;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final class EntityMapUtils {

    private EntityMapUtils() {
    }

    public static List<Creature> getCreatures(EntityMap map) {
        return getCellsWithCreatures(map).values().stream().toList();
    }

    public static void cleanMapFromDeadCreatures(EntityMap map) {
        for (Creature creature : getCreatures(map)) {
            if (creature.isDead()) {
                map.clearCell(creature.getCoordinates());
            }
        }
    }

    public static boolean hasMapCell(Coordinates coordinates, EntityMap entityMap) {
        int border = entityMap.size();
        int x = coordinates.getX();
        int y = coordinates.getY();
        if (x < 0 || y < 0) {
            return false;
        }
        return (x < border) && (y < border);
    }

    public static List<Coordinates> getVoidCells(EntityMap entityMap) {
        return entityMap.entrySet()
                .stream()
                .filter(e -> e.getValue() == null)
                .map(e -> e.getKey())
                .toList();
    }

    public static List<Entity> getNotNullEntities(EntityMap entityMap) {
        return entityMap.values().stream().filter(Objects::nonNull).toList();
    }

    public static Map<Coordinates, Creature> getCellsWithCreatures(EntityMap entityMap) {
        return entityMap.entrySet().stream()
                .filter(entry -> {
                    if (entry.getValue() == null) {
                        return false;
                    }
                    EntityType type = entry.getValue().getType();
                    return type.equals(EntityType.HERBIVORE) || type.equals(EntityType.PREDATOR);
                })
                .collect(Collectors
                        .toMap(Map.Entry::getKey, e -> (Creature) e.getValue()));
    }
}
