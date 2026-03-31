package util;

public class Messages {

    public static void showWelcome() {
        System.out.println("+-----------------------------------------+");
        System.out.println("|     WELCOME TO CAR RENTAL SYSTEM       |");
        System.out.println("+-----------------------------------------+");
        System.out.println();
        System.out.println("  Find the perfect car for your trip");
        System.out.println();
    }

    public static void showFatalError() {
        System.out.println();
        System.out.println("+-----------------------------------------+");
        System.out.println("|  ERROR: System unavailable.            |");
        System.out.println("|  Please try again later.               |");
        System.out.println("+-----------------------------------------+");
    }
    
    public static void showInvalidInput() {
        System.out.println();
        System.out.println("+-----------------------------------------+");
        System.out.println("|  ERROR: Invalid input detected.        |");
        System.out.println("|  Please restart the application.       |");
        System.out.println("+-----------------------------------------+");
    }
}