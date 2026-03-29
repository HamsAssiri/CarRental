package util;

import java.util.Scanner;
import model.UserInput;
import validation.InputValidator;

public class InputHandler {

    private final Scanner scanner = new Scanner(System.in); // Least Privilege: field خاصة

    public UserInput getUserInput() {
        int passengers = readPassengers();
        int days = readDays();
        int mileage = readMileage();

        return new UserInput(passengers, days, mileage);
    }

    // دوال خاصة لكل نوع من البيانات
    private int readPassengers() {
        int value;
        while (true) {
            System.out.print("Enter number of passengers (1-10): ");
            String inputLine = scanner.nextLine(); // اقرأ السطر كله
            try {
                value = Integer.parseInt(inputLine); // حاول تحويله لرقم
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue; // عد للحلقة
            }
            if (!InputValidator.validatePassengers(value)) {
                System.out.println("Value must be between 1 and " + 10);
                continue; // عد للحلقة
            }
            break; // صحيح، اكسر الحلقة
        }
        return value;
    }

    private int readDays() {
        int value;
        while (true) {
            System.out.print("Enter number of rental days (1-365): ");
            String inputLine = scanner.nextLine(); // غيّر هنا
            try {
                value = Integer.parseInt(inputLine); // غيّر هنا
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
            if (!InputValidator.validateDays(value)) {
                System.out.println("Value must be between 1 and 365.");
                continue;
            }
            break;
        }
        return value;
    }
    
    private int readMileage() {
        int value;
        while (true) {
            System.out.print("Enter approximate trip mileage (1-100000): ");
            String inputLine = scanner.nextLine(); // غيّر هنا
            try {
                value = Integer.parseInt(inputLine); // غيّر هنا
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
            if (!InputValidator.validateMileage(value)) {
                System.out.println("Value must be between 1 and 100000.");
                continue;
            }
            break;
        }
        return value;
    }
}