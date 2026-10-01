package resources;
import model.pathfind.BfsPathFinder;
import model.pathfind.PathFinder;

public final class SimulationConfig {
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

    public static final int MAX_TURNS_WITHOUT_FOOD_SPAWN = 4;
    public static final int MIN_FOOD_QUANTITY_TO_CREATE = 1;
    public static final int MIN_PREDATORS_QUANTITY_TO_CREATE = 2;
    public static final int HUNGER_HP_LOSS = 1;

    public static final int CREATURE_MAX_TURNS_WITHOUT_FOOD_BEFORE_HUNGER = 7;

    public static final int PREDATOR_MAX_HEALTH_POINTS = 10;
    public static final int PREDATOR_MAX_SPEED = 2;
    public static final int PREDATOR_LOW_SPEED = 1;
    public static final double PREDATOR_ATTACK_CHANCE = 0.7;
    public static final int PREDATOR_ATTACK_POWER = 3;

    public static final int HERBIVORE_MAX_HEALTH_POINTS = 15;
    public static final int HERBIVORE_SPEED = 1;

    public static final String WORD_TO_EXIT = "exit";
    public static final String WORD_TO_PAUSE_SIMULATION = "stop";
    public static final String WORD_TO_START_SIMULATION = "start";
    public static final String WORD_TO_SHOW_NEXT_TURN = "next";

}
