package Util;

import java.util.Scanner;

public class InputHandler {
    private static Scanner scanner = new Scanner(System.in);

    public static int getIntInput(String message){
        System.out.print(message);
        int input = scanner.nextInt();

        return input;
    }

    public static float getFloatInput(String message){
        System.out.print(message);
        float input = scanner.nextFloat();

        return input;
    }

    public static String getStringInput(String message){
        System.out.print(message);
        String input = scanner.nextLine();

        return input;
    }

    public static void limparBuffer(){
        scanner.nextLine();
    }
}
