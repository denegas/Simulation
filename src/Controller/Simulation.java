package Controller;
import Model.Coordinates;
import Model.Entity;
import Model.EntityMap;

import java.util.Map;

public final class Simulation {
 private static int MAP_SIZE;
 private static int turnsCounter = 0;
 private static EntityMap map;

    public static void setMap(EntityMap map) {
        Simulation.map = map;
        MAP_SIZE = map.getSIZE();
    }
 public static Map<Coordinates,Entity> getMap(){
        return map.getMap();
 }
 public static int getMapSize(){
     return Simulation.MAP_SIZE;
 }

    public void nextTurn(){
 }
 public void startSimulation(){

 }
 public void pauseSimulation(){

 }

}
