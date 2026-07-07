package Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class EntityMap {
    public EntityMap(int size) {
        this.SIZE = size;
    }

    private final int SIZE;
    private final Map<Coordinates, Entity> map = new HashMap<>();//будет хранить координаты всех сущностей

    public Map<Coordinates, Entity> getMap() {
        return map;
    }

    public void add(Coordinates coordinates, Entity entity) {
        map.put(coordinates, entity);
    }

    public void add(Coordinates coordinates) {
        map.put(coordinates, null);
    }

    public void remove(Coordinates coordinates, Entity entity) {
        map.remove(coordinates, entity);
    }

    public int getSIZE() {
        return this.SIZE;
    }

    public List<Entity> getNotNullEntities() {
        return map.values().stream().filter(Objects::nonNull).toList();
    }

    public List<Coordinates> getVoidCells() {
        return map.entrySet()
                .stream()
                .filter(e -> e.getValue() == null)
                .map(e -> e.getKey())
                .toList();
    }

    public Map<Coordinates, Creature> getCreatures() {
        return map.entrySet().stream()
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
