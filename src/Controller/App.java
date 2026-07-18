package Controller;

import View.ConsoleWriter;
import View.renderer.RenderMode;

import java.util.Optional;

public class App {
    public void start() {
        ConsoleWriter.printHello();
        ConsoleWriter.printMapSizeAsk();
        Simulation.initialize(ConsoleInput.getMapSize());

        ConsoleWriter.printRenderModes();
        RenderMode renderMode = ConsoleInput.getRenderMode();

        ConsoleWriter.printRepeatsAsk();
        Optional<Integer> repeats = ConsoleInput.getTurnRepeat();

        if (repeats.isEmpty()) {
            Simulation.startSimulation(renderMode);
        } else {
            renderMode.run(repeats.get());
        }

        ConsoleInput.closeScanner();
    }
}