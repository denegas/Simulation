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
    public static final double HERBIVORE_SPAWN_CHANCE_PER_ONE_CELL = 0.14;
    public static final double PREDATOR_SPAWN_CHANCE_PER_ONE_CELL = 0.1;
    public static final double GRASS_SPAWN_CHANCE_PER_ONE_CELL = 0.09;
    public static final double TREE_SPAWN_CHANCE_PER_ONE_CELL = 0.05;
    public static final double ROCK_SPAWN_CHANCE_PER_ONE_CELL = 0.03;
}
