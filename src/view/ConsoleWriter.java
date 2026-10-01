package view;


public final class ConsoleWriter {

    private ConsoleWriter() {
    }

    public static void printHello() {
        System.out.println("Hello! That's Simulation, and you have to chose settings");
    }

    public static void printMapSizeAsk() {
        System.out.println("write a map size(map height and map width)");
    }

    public static void printMapSizeError(int minMapSize,int maxMapSize) {
        System.out.println("Map size must be between " +
                minMapSize + " and " + maxMapSize + "!");
    }

    public static void printRepeatsAsk() {
        System.out.println("write a repeat times or write \"inf\" to start infinity simulation");
    }

    public static void printPositiveNumberError() {
        System.out.println("Enter a positive number!");
    }


    public static void printStringInsteadOfNumberError() {
        System.out.println("You must enter a number!");
    }


    public static void printTurn(int turn) {
        System.out.println("Turn: " + turn);
    }

    public static void printSimulationIsWaiting(){
        System.out.println("Simulation is waiting for start...");
    }
    public static void printCommands(String startCommand, String pauseCommand, String exitCommand){
        System.out.printf("Please write one of these commands ( %s | %s | %s )\n".formatted(startCommand,pauseCommand,exitCommand));
    }
}
