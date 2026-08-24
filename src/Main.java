import controller.Simulation;
import model.entitymap.EntityMap;
import view.renderer.ConsoleRenderer;
import view.renderer.Renderer;

public class Main {
    public static void main(String[] args) {
        EntityMap entityMap = new EntityMap(2,3);
        Renderer consoleRenderer = new ConsoleRenderer();
        Simulation simulation = new Simulation(entityMap, consoleRenderer);
        simulation.nextNTurns(250);

    }
}
