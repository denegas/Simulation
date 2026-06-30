package Controller;
import Model.EntityMap;
import java.util.List;

public final class Simulation {
 private static final int MAP_SIZE = 10;
 private static int turnsCounter = 0;
    private static EntityMap map;
   // private static final List<Action> initActions = List.of(new MapCreator(MAP_SIZE), new EntityCreator(map));
    private static final List<Action> turnActions = List.of(new AllCreaturesMove());

    public static void setMap(EntityMap map) {
        Simulation.map = map;
    }
 public static EntityMap getEntityMap(){
        return map;
 }
 public static int getMapSize(){
     return Simulation.MAP_SIZE;
 }
 public static void initialize(int mapSize){
        MapCreator mapCreator = new MapCreator(mapSize);
        mapCreator.execute();
        EntityCreator entityCreator = new EntityCreator(map);
        entityCreator.execute();
 }
    public static void nextTurn(){
    for (Action turnAction: turnActions){
        turnAction.execute();
    }
    turnsCounter++;
 }
 public static void nextNTurns(int n){
        for(int i = 0;i<n;i++){
            nextTurn();
        }
 }
 public void startSimulation(){

 }
 public void pauseSimulation(){

 }

}
