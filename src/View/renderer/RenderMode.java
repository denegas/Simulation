package View.renderer;

import Controller.Simulation;

import java.util.Optional;
import java.util.function.IntConsumer;

public enum RenderMode {
    TURN_BY_TURN(1, Simulation::nextNTurns),
    CREATURE_TICKS(2, Simulation::nTicks);

    private final int code;
    private final IntConsumer runner;

    RenderMode(int code, IntConsumer runner) {
        this.code = code;
        this.runner = runner;
    }

    public void run(int repeats) {
        runner.accept(repeats);
    }

    public static Optional<RenderMode> fromCode(int code) {
        for (RenderMode mode : values()) {
            if (mode.code == code) {
                return Optional.of(mode);
            }
        }
        return Optional.empty();
    }
}
