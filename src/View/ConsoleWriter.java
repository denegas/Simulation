package View;


public final class ConsoleWriter {

    private ConsoleWriter(){}

    public static void printOptions(){
        System.out.println("""
                1. Render after one turn
                2. Render after each animal move""");
    }
    public static void printRepeatsAsk(){
        System.out.println("write a repeat times");
    }
    public static void printMapSizeAsk(){
        System.out.println("write a map size(map height and map width)");
    }
    public static void printHello(){
        System.out.println("Hello! That's Simulation, and you have to chose settings");
    }
    public static void printError(){
        System.out.println("error");
    }
    public static void printTurn(int turn){
        System.out.println("Turn: " + turn);
    }
}
