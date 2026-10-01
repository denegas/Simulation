package controller;


import view.ConsoleWriter;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimulationManager {
    private final Simulation simulation;
    private final Scanner SCANNER;
    private final ExecutorService service = Executors.newFixedThreadPool(2);
    private final Object lock = new Object();
    private static final String WORD_TO_EXIT = "exit";
    private static final String WORD_TO_PAUSE_SIMULATION = "stop";
    private static final String WORD_TO_START_SIMULATION = "start";

    private volatile boolean terminated = false;

    public SimulationManager(Simulation simulation) {
        this.simulation = simulation;
        this.SCANNER = new Scanner(System.in);
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
                String line = SCANNER.nextLine().trim().toLowerCase();

                switch (line) {
                    case WORD_TO_PAUSE_SIMULATION -> simulation.pauseSimulation();
                    case WORD_TO_START_SIMULATION -> {
                        simulation.isRunning = true;
                        synchronized (lock) {

                            lock.notify();
                        }
                    }
                    case WORD_TO_EXIT -> {
                        synchronized (lock) {
                            terminated = true;
                            simulation.pauseSimulation();
                            SCANNER.close();
                            lock.notify();
                        }
                    }
                    default ->{
                        ConsoleWriter.printCommands();
                    }
                }
            }
        });

        service.shutdown();

    }

}
