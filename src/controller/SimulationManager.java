package controller;


import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimulationManager {
    private final Simulation simulation;
    private final Scanner SCANNER;
    private final ExecutorService service = Executors.newFixedThreadPool(2);
    private final Object lock = new Object();

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
                            System.out.println("waiting");
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
                    case "stop" -> simulation.pauseSimulation();
                    case "start" -> {
                        simulation.isRunning = true;
                        synchronized (lock) {

                            lock.notify();
                        }
                    }
                    case "br" -> {
                        synchronized (lock) {
                            terminated = true;
                            simulation.pauseSimulation();
                            SCANNER.close();
                            lock.notify();
                        }
                    }
                    default ->{
                        System.out.println("Please write one of these commands (start|stop|br)");
                    }
                }
            }
        });

        service.shutdown();

    }

}
