package resources;
import model.pathfind.BfsPathFinder;
import model.pathfind.PathFinder;

public class SimulationConfig {
    private SimulationConfig() {

    }

    public static final PathFinder PATH_FINDER = new BfsPathFinder();
}
