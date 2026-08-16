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
        entities.put(coordinates, entity);
    }

    public void add(Coordinates coordinates) {
        entities.put(coordinates, null);
    }

    public void clearCell(Coordinates coordinates) {
        entities.put(coordinates, null);
    }

    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }

    public Entity get(Coordinates coordinates) {
        return entities.get(coordinates);
    }

    public Set<Coordinates> keySet() {
        return entities.keySet();
    }

    public Collection<Entity> values() {
        return entities.values();
    }

    public Set<Map.Entry<Coordinates, Entity>> entrySet() {
        return entities.entrySet();
    }

}
