package Model;

import java.util.HashMap;
import java.util.Map;

public class EntityMap {
private final static Map<Coordinates, Entity> map = new HashMap<>();//будет хранить координаты всех сущностей
 public static Map<Coordinates,Entity> getMap(){
     return map;
 }

 public static void add(Coordinates coordinates,Entity entity){
     map.put(coordinates, entity);
 }
    public static void add(Coordinates coordinates){
        map.put(coordinates,null);
    }
 public static void remove(Coordinates coordinates,Entity entity){
     map.remove(coordinates,entity);
 }

}
