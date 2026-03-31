package util;

import java.util.Locale;
import java.util.Scanner;
import model.UserInput;
import validation.InputValidator;

public class InputHandler {

    private final Scanner scanner = new Scanner(System.in); // Least Privilege: field خاصة

     public InputHandler() {
        // Force scanner to use English locale
        scanner.useLocale(Locale.ENGLISH);
    } 

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
            System.out.print("Enter number of passengers (1-7): ");
            String inputLine = scanner.nextLine().trim(); // اقرأ السطر كله
            // Convert any Arabic numerals to English numerals
            inputLine = convertToEnglishNumbers(inputLine);

            try {
                value = Integer.parseInt(inputLine); // حاول تحويله لرقم
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue; // عد للحلقة
            }
            if (!InputValidator.validatePassengers(value)) {
                System.out.println("Value must be between 1 and 7");
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
            String inputLine = scanner.nextLine().trim();; // غيّر هنا
            inputLine = convertToEnglishNumbers(inputLine);

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
            String inputLine = scanner.nextLine().trim(); // غيّر هنا
            inputLine = convertToEnglishNumbers(inputLine);

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

    /**
     * Convert Arabic numerals (٠-٩) to English numerals (0-9)
     */
    private String convertToEnglishNumbers(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        
        // Arabic to English numeral mapping
        char[] arabicNumerals = {'٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩'};
        char[] englishNumerals = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        
        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            boolean isArabic = false;
            for (int i = 0; i < arabicNumerals.length; i++) {
                if (c == arabicNumerals[i]) {
                    result.append(englishNumerals[i]);
                    isArabic = true;
                    break;
                }
            }
            if (!isArabic) {
                result.append(c);
            }
        }
        return result.toString();
    }

}