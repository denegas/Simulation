package controller;

import model.entitymap.EntityMap;
import model.actions.Action;
import model.actions.turnActions.AllCreaturesMove;
import model.actions.initializeActions.InitializeEntityCreator;
import model.actions.turnActions.TurnEntityCreator;
import resources.SimulationConfig;
import view.ConsoleWriter;
import view.renderer.Renderer;

import java.util.List;

public final class Simulation {
    public volatile boolean isRunning = false;

    private final Renderer renderer;
    private final EntityMap entityMap;

    private static final List<Action> initActions = List.of(new InitializeEntityCreator());
    private static final List<Action> turnActions = List.of(new AllCreaturesMove(), new TurnEntityCreator());


    private int turnsCounter = 0;


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

    public void startSimulation() {
        isRunning = true;

        while (isRunning){
            nextTurn();
            sleep(SimulationConfig.TURN_SLEEP_MC);
        }

    }

    public void pauseSimulation() {
        isRunning = false;
    }


    public void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
