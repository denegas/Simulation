import Controller.EntityCreator;
import Controller.MapCreator;
import Model.*;

import java.util.HashMap;
import java.util.Map;

class Main{
    public static void main(String[] args) {
        MapCreator mapCreator = new MapCreator();
        mapCreator.execute();

        EntityCreator entityCreator = new EntityCreator();
        entityCreator.execute();
        Map<Coordinates,Entity> map = new HashMap<>(EntityMap.getMap());
        map.forEach((k,v)-> {
            if(v != null){
            System.out.println(k.getCoordinateX() + " : " + k.getCoordinateY() + v.getType().getEntityView());
        }})
    ;}
}
