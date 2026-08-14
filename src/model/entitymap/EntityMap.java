package model.entitymap;

import model.entities.Entity;
import model.entities.EntityType;
import model.entities.creatures.Creature;

import java.util.*;
import java.util.stream.Collectors;

public class EntityMap {

    private final int size;
    private final Map<Coordinates, Entity> entities = new HashMap<>();

    public EntityMap(int size) {
        this.size = size;
    }

    public void add(Coordinates coordinates, Entity entity) {
        entities.put(coordinates, entity);
    }

    public void add(Coordinates coordinates) {
        entities.put(coordinates, null);
    }

    public void clearCell(Coordinates coordinates) {
        entities.put(coordinates, null);
    }

    public int size() {
        return this.size;
    }
    public Entity get(Coordinates coordinates){
        return entities.get(coordinates);
    }
    public Set<Coordinates> keySet(){
        return entities.keySet();
    }
    public Collection<Entity> values(){
        return entities.values();
    }

    public List<Entity> getNotNullEntities() {
        return entities.values().stream().filter(Objects::nonNull).toList();
    }

    public List<Coordinates> getVoidCells() {
        return entities.entrySet()
                .stream()
                .filter(e -> e.getValue() == null)
                .map(e -> e.getKey())
                .toList();
    }

    public Map<Coordinates, Creature> getCellsWithCreatures() {
        return entities.entrySet().stream()
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
