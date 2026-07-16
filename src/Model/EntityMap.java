package Model;

import java.util.*;
import java.util.stream.Collectors;

public class EntityMap {

    private final int SIZE;
    private final Map<Coordinates, Entity> map = new HashMap<>();

    public EntityMap(int size) {
        this.SIZE = size;
    }

    public void add(Coordinates coordinates, Entity entity) {
        map.put(coordinates, entity);
    }

    public void add(Coordinates coordinates) {
        map.put(coordinates, null);
    }

    public void removeEntity(Coordinates coordinates) {
        map.put(coordinates, null);
    }

    public int size() {
        return this.SIZE;
    }
    public Entity get(Coordinates coordinates){
        return map.get(coordinates);
    }
    public Set<Coordinates> keySet(){
        return map.keySet();
    }
    public Collection<Entity> values(){
        return map.values();
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
