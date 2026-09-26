import controller.Simulation;
import controller.SimulationManager;
import model.entitymap.EntityMap;
import view.renderer.ConsoleRenderer;
import view.renderer.Renderer;


public class Main {

    public static void main(String[] args) {

        EntityMap entityMap = new EntityMap(10,10);
        Renderer consoleRenderer = new ConsoleRenderer();
        Simulation simulation = new Simulation(entityMap, consoleRenderer);

        SimulationManager manager = new SimulationManager(simulation);
        manager.execute();
    }
}
