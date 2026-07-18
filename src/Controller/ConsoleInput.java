package Controller;

import View.ConsoleWriter;
import View.renderer.RenderMode;

import java.util.Optional;
import java.util.Scanner;
import java.util.function.IntFunction;

public final class ConsoleInput {
    public static final Scanner SCANNER = new Scanner(System.in);
    private static final String WORD_TO_START_INFINITY_SIMULATION = "inf";

    private ConsoleInput() {
    }

    public static int getMapSize() {
        return readValidInt(
                mapSize -> (mapSize >= Simulation.MIN_MAP_SIZE && mapSize <= Simulation.MAX_MAP_SIZE)
                        ? Optional.of(mapSize) : Optional.empty(),
                ConsoleWriter::printMapSizeError
        );
    }

    public static RenderMode getRenderMode() {
        return readValidInt(RenderMode::fromCode, ConsoleWriter::printRenderModError);
    }

    public static Optional<Integer> getTurnRepeat() {
        while (true) {
            String input = SCANNER.nextLine().trim();

            if (input.equalsIgnoreCase(WORD_TO_START_INFINITY_SIMULATION)) {
                return Optional.empty();
            }

            Integer repeats = tryParse(input);
            if (repeats == null) {
                ConsoleWriter.printStringInsteadOfNumberError();
                continue;
            }
            if (repeats < 1) {
                ConsoleWriter.printPositiveNumberError();
                continue;
            }
            return Optional.of(repeats);
        }
    }

    private static <T> T readValidInt(IntFunction<Optional<T>> validate, Runnable onInvalid) {
        while (true) {
            Integer value = tryParse(SCANNER.nextLine().trim());
            if (value == null) {
                ConsoleWriter.printStringInsteadOfNumberError();
                continue;
            }
            Optional<T> result = validate.apply(value);
            if (result.isEmpty()) {
                onInvalid.run();
                continue;
            }
            return result.get();
        }
    }

    private static Integer tryParse(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}