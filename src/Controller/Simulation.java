package Controller;
import Model.EntityMap;
import View.ConsoleInput;
import View.ConsoleWriter;

import java.util.List;

public final class Simulation {
 private static int MAP_SIZE;
 private static int turnsCounter = 0;
    private static EntityMap map;
    private static final List<Action> initActions = List.of(new MapCreator(), new InitializeEntityCreator());
    private static final List<Action> turnActions = List.of(new AllCreaturesMove(), new TurnEntityCreator());

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
        map = new EntityMap(mapSize);
        for (Action initAction:initActions){
            initAction.execute(map);
        }

 }
    public static void nextTurn(){
    for (Action turnAction: turnActions){
        turnAction.execute(map);
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
     RenderEveryCreatureAtTurn renderEveryCreatureAtTurn = new RenderEveryCreatureAtTurn();
     TurnEntityCreator turnEntityCreator = new TurnEntityCreator();
        for (int i = 0;i<n;i++) {
            renderEveryCreatureAtTurn.execute(map);
            turnEntityCreator.execute(map);
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
