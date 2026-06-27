package Controller;
import Model.Coordinates;
import Model.Entity;

import java.util.Map;

public class Simulation {
 private static final int MAP_SIZE =10;
 private static int turnsCounter = 0;
 private static Map<Coordinates, Entity> map;

    public static void setMap(Map<Coordinates, Entity> map) {
        Simulation.map = map;

    }
 public static Map<Coordinates,Entity> getMap(){
        return map;
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
