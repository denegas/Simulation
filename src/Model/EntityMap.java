package Model;

import java.util.HashMap;
import java.util.Map;

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
    public int getSIZE(){
        return this.SIZE;
    }

}
