package resources;
import model.pathfind.BfsPathFinder;
import model.pathfind.PathFinder;

public class SimulationConfig {
    private SimulationConfig() {

    }

    public static final PathFinder PATH_FINDER = new BfsPathFinder();
    public static final int TURN_SLEEP_MC = 1800;
    public static final int TICK_SLEEP_MC = 500;
    public static final int MIN_MAP_SIZE = 4;
    public static final int MAX_MAP_SIZE = 50;
}
