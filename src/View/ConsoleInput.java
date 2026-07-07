package View;

import java.io.IOException;
import java.util.Scanner;

public final class ConsoleInput {
    private static final Scanner scanner = new Scanner(System.in);
    private ConsoleInput(){}
    public static int getInt() {
        try{
            return scanner.nextInt();
        } catch (RuntimeException e){
            throw new RuntimeException(e);
        }

    }
//    public static int getDecision(){
//        try {
//            return scanner.nextInt();
//        }catch (RuntimeException e){
//            throw new RuntimeException(e);
//        }
//    }
}
