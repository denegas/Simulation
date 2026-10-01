package controller;


import resources.SimulationConfig;
import view.ConsoleInput;
import view.ConsoleWriter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimulationManager {
    private final Simulation simulation;
    private final ExecutorService service = Executors.newFixedThreadPool(2);
    private final Object lock = new Object();


    private volatile boolean terminated = false;

    public SimulationManager(Simulation simulation) {
        this.simulation = simulation;
    }

    public void execute() {
        service.submit(() -> {

            while (true) {
                synchronized (lock) {
                    while (!simulation.isRunning && !terminated) {
                        try {
                            ConsoleWriter.printSimulationIsWaiting();
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                }
                if (terminated){
                   return;
                }
                simulation.startSimulation();
            }
        });
        service.submit(() -> {

            while (!terminated) {
                String line = ConsoleInput.getString().trim().toLowerCase();

                switch (line) {
                    case SimulationConfig.WORD_TO_PAUSE_SIMULATION -> simulation.pauseSimulation();
                    case SimulationConfig.WORD_TO_START_SIMULATION -> {
                        simulation.isRunning = true;
                        synchronized (lock) {

                            lock.notify();
                        }
                    }
                    case SimulationConfig.WORD_TO_EXIT -> {
                        synchronized (lock) {
                            terminated = true;
                            simulation.pauseSimulation();
                            lock.notify();
                        }
                    }
                    case SimulationConfig.WORD_TO_SHOW_NEXT_TURN -> {
                        simulation.nextTurn();
                    }
                    default ->{
                        ConsoleWriter.printCommands(SimulationConfig.WORD_TO_START_SIMULATION,
                                SimulationConfig.WORD_TO_PAUSE_SIMULATION,
                                SimulationConfig.WORD_TO_SHOW_NEXT_TURN,
                                SimulationConfig.WORD_TO_EXIT);
                    }
                }
            }
        });

        service.shutdown();

    }

}
