package model.entitymap;

import model.entities.Entity;
import java.util.*;

public class EntityMap {

    private final int width;
    private final int height;
    private final Map<Coordinates, Entity> entities = new HashMap<>();

    public EntityMap(int width,int height) {
        this.width = width;
        this.height = height;
    }

    public void add(Coordinates coordinates, Entity entity) {
        validate(coordinates);
        entities.put(coordinates, entity);
    }

    public void clearCell(Coordinates coordinates) {
        validate(coordinates);
        entities.remove(coordinates);
    }

    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }

    public Optional<Entity> get(Coordinates coordinates) {
        validate(coordinates);
        return Optional.ofNullable(entities.get(coordinates));
    }

    public Collection<Entity> values() {
        return entities.values();
    }

    public Set<Map.Entry<Coordinates, Entity>> entrySet() {
        return entities.entrySet();
    }

    private void validate(Coordinates coordinates){
        int x = coordinates.x();
        int y = coordinates.y();
        if((x >= this.width || y >= this.height) || x < 0 || y < 0) {
            throw new RuntimeException("entityMap doesnt has coordinates: " + coordinates);
        }
    }

}
