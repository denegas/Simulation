package Controller;

import Model.map.EntityMap;
import Model.actions.Action;
import Model.actions.turnActions.AllCreaturesMove;
import Model.actions.initializeActions.InitializeEntityCreator;
import Model.actions.initializeActions.MapCreator;
import Model.actions.turnActions.MoverAndRendererEachCreature;
import Model.actions.turnActions.TurnEntityCreator;
import View.ConsoleInput;
import View.ConsoleWriter;
import View.renderer.ConsoleRenderer;
import java.util.List;

public final class Simulation {
    public static final ConsoleRenderer CONSOLE_RENDERER = new ConsoleRenderer();
    public static final int TURN_SLEEP_MC = 1800;
    public static final int TICK_SLEEP_MC = 500;

    private static final List<Action> initActions = List.of(new MapCreator(), new InitializeEntityCreator());
    private static final List<Action> turnActions = List.of(new AllCreaturesMove(), new TurnEntityCreator());
    private static final List<Action> turnActionsForNTicks = List.of(new MoverAndRendererEachCreature(), new TurnEntityCreator());


    private static int turnsCounter = 0;
    private static EntityMap map;

    public static void setMap(EntityMap map) {
        Simulation.map = map;
    }

    public static void initialize(int mapSize) {
        map = new EntityMap(mapSize);
        for (Action initAction : initActions) {
            initAction.execute(map);
        }
    }

    public static void nextTurn() {
        for (Action turnAction : turnActions) {
            turnAction.execute(map);
        }

        turnsCounter++;
        ConsoleWriter.printTurn(turnsCounter);
    }

    public static void nextNTurns(int repeatTimes) {
        for (int i = 0; i < repeatTimes; i++) {
            nextTurn();
            sleep(TURN_SLEEP_MC);
        }
    }

    // nextTurnWithEachCreatureMoveRender
    public static void nTicks(int repeatTimes) {
        CONSOLE_RENDERER.render(map);

        for (int i = 0; i < repeatTimes; i++) {
            for (Action turnAction : turnActionsForNTicks ){
                turnAction.execute(map);
            }
            ConsoleWriter.printTurn(turnsCounter);
            turnsCounter++;

        }
    }

    public void startSimulation() {

    }

    public void pauseSimulation() {

    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void userConfiguresSettings() {
        ConsoleWriter.printHello();
        ConsoleWriter.printMapSizeAsk();

        initialize(ConsoleInput.getInt());
        ConsoleWriter.printOptions();
        switch (ConsoleInput.getInt()) {
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
