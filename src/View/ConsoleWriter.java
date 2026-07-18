package View;

import Controller.Simulation;

public final class ConsoleWriter {

    private ConsoleWriter() {
    }

    public static void printRenderMods() {
        System.out.println("""
                1. Render after one turn
                2. Render after each animal move""");
    }

    public static void printRepeatsAsk() {
        System.out.println("write a repeat times or write \"inf\" to start infinity simulation");
    }

    public static void printMapSizeAsk() {
        System.out.println("write a map size(map height and map width)");
    }

    public static void printHello() {
        System.out.println("Hello! That's Simulation, and you have to chose settings");
    }

    public static void printPositiveNumberError() {
        System.out.println("Enter a positive number!");
    }

    public static void printMapSizeError() {
        System.out.println("Map size must be between " +
                Simulation.MIN_MAP_SIZE + " and " + Simulation.MAX_MAP_SIZE + "!");
    }

    public static void printStringInsteadOfNumberError() {
        System.out.println("You must enter a number!");
    }

    public static void printRenderModError() {
        System.out.println("You must choose one of available render mods!");
    }

    public static void printTurn(int turn) {
        System.out.println("Turn: " + turn);
    }
}
