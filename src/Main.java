import controller.App;
import controller.Simulation;
import model.entities.environment.Rock;
import model.entitymap.Coordinates;
import model.entitymap.EntityMap;

class Main {
    public static void main(String[] args) {
        Simulation.initialize(15,4);
        Simulation.nextNTurns(500);

    }
}
