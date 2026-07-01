package Controller;
import Model.EntityMap;
import View.ConsoleInput;
import View.ConsoleWriter;
import View.Renderer;

import java.util.List;

public final class Simulation {
 private static int MAP_SIZE;
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
 public static void setMapSize(int mapSize){MAP_SIZE = mapSize;}
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
        for(int i = 0;i<n;i++) {
            nextTurn();
            pause(1800);
        }

 }
 public static void nTicks(int n) { // nextTurnWithEachCreatureMoveRender
        for (int i = 0;i<n;i++) {
            RenderEveryCreatureAtTurn renderEveryCreatureAtTurn = new RenderEveryCreatureAtTurn();
            renderEveryCreatureAtTurn.execute();
            turnsCounter++;

        }
 }
 public void startSimulation(){

 }
 public void pauseSimulation(){

 }
 public static void pause(int millis){
     try {
         Thread.sleep(millis);
     } catch (InterruptedException e) {
         throw new RuntimeException(e.getMessage());
     }
 }
 public static void userConfiguresSettings(){
     ConsoleWriter.printHello();
     ConsoleWriter.printMapSizeAsk();

     initialize(ConsoleInput.getInt());
     ConsoleWriter.printOptions();
     switch (ConsoleInput.getInt()){
         case 1:
             ConsoleWriter.printRepeatsAsk();
             nextNTurns(ConsoleInput.getInt());
             break;
         case 2:
             ConsoleWriter.printRepeatsAsk();
             nTicks(ConsoleInput.getInt());
             break;
         default:
             ConsoleWriter.printError();
     }
 }
}
