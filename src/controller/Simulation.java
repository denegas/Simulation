package controller;

import model.entitymap.EntityMap;
import model.actions.Action;
import model.actions.turnActions.AllCreaturesMove;
import model.actions.initializeActions.InitializeEntityCreator;
import model.actions.initializeActions.MapCreator;
import model.actions.turnActions.TurnEntityCreator;
import view.ConsoleWriter;
import view.renderer.Renderer;

import java.util.List;

public final class Simulation {
    public static final int TURN_SLEEP_MC = 1800;
    public static final int TICK_SLEEP_MC = 500;
    public static final int MIN_MAP_SIZE = 4;
    public static final int MAX_MAP_SIZE = 50;
    public Renderer renderer;

    private static final List<Action> initActions = List.of(new MapCreator(), new InitializeEntityCreator());
    private static final List<Action> turnActions = List.of(new AllCreaturesMove(), new TurnEntityCreator());
    private static final List<Action> turnActionsForNTicks = List.of(new TurnEntityCreator());


    private static int turnsCounter = 0;
    private EntityMap entityMap;

    public Simulation(EntityMap entityMap, Renderer renderer) {
        this.renderer = renderer;
        this.entityMap = entityMap;

        for (Action initAction : initActions) {
            initAction.execute(entityMap);
        }
    }

    public void nextTurn() {
        for (Action turnAction : turnActions) {
            turnAction.execute(entityMap);
        }
        renderer.render(entityMap);
        turnsCounter++;

        ConsoleWriter.printTurn(turnsCounter);
    }

    public void nextNTurns(int repeatTimes) {
        for (int i = 0; i < repeatTimes; i++) {
            nextTurn();
            sleep(TURN_SLEEP_MC);
        }
    }

    // nextTurnWithEachCreatureMoveRender
    public void nTicks(int repeatTimes) {
        renderer.render(entityMap);

        for (int i = 0; i < repeatTimes; i++) {
            for (Action turnAction : turnActionsForNTicks) {
                turnAction.execute(entityMap);
            }
            ConsoleWriter.printTurn(turnsCounter);
            turnsCounter++;

        }
    }

    public void startSimulation() {

    }

    public void pauseSimulation() {

    }


    public void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
