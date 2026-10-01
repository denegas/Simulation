package view;



import java.util.Scanner;

public final class ConsoleInput {
    private static final Scanner SCANNER = new Scanner(System.in);


    private ConsoleInput() {
    }

    public static String getString(){
        return SCANNER.nextLine();
    }

    public static void closeScanner(){
        SCANNER.close();
    }

}