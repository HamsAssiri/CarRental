import model.CarLoader;
import model.ICar;
import model.UserInput;
import model.CarWithDetails;
import service.CostCalculator;
import service.CarFilterService;
import util.InputHandler;
import util.Messages;
import display.CarDisplay;
import display.ResultDisplay;
import validation.InputValidator;

import java.util.List;

/* 
//for it to be runned perfectly on any device and any language 
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
*/

// to fix any format problem 
import java.util.Locale;

import javax.swing.SwingUtilities;
import java.util.Scanner;

public class CarRentalApp {

    public static void main(String[] args) {
        // Ask user which interface they want
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select Interface:");
        System.out.println("1. Terminal (Console)");
        System.out.println("2. Graphical (GUI)");

       String choice = "";
        boolean valid = false;
        
        // Loop until valid input (1 or 2)
        while (!valid) {
            System.out.print("  Enter your choice (1 or 2): ");
            choice = scanner.nextLine().trim();
            
            if (choice.equals("1") || choice.equals("2")) {
                valid = true;
            } else {
                System.out.println("\n  Invalid choice! Please enter 1 or 2.\n");
            }
        }
        
        System.out.println();
        
        if (choice.equals("2")) {
            // Launch GUI
            System.out.println("  Launching Graphical Interface...");
            SwingUtilities.invokeLater(() -> {
                gui.CarRentalGUI.main(new String[0]);
            });
        } else {
            // Run console version
            System.out.println("  Launching Console Interface...\n");
            consoleMain(args);
        }
        
        scanner.close();
    }

       private static void consoleMain(String[] args) {
/* 
         // Fix console encoding to support UTF-8
        try {
            System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8.name()));
        } catch (UnsupportedEncodingException e) {
            // Fallback to default if UTF-8 not supported
            System.out.println("Warning: UTF-8 encoding not supported");
        }
*/

         // Force English locale for number formatting to ensure proper display
        Locale.setDefault(Locale.ENGLISH);
        System.setProperty("user.country", "US");
        System.setProperty("user.language", "en");
        
        // Override default formatter for numbers
        System.setProperty("user.country", "US");
        System.setProperty("user.language", "en");

        Messages.showWelcome();

        // Load cars from JSON file
        CarLoader loader = new CarLoader();
        List<ICar> cars = loader.loadCars();

        // Fail safe: check if cars loaded successfully
        if (cars == null || cars.isEmpty()) {
            Messages.showFatalError();
            return;
        }

        // Display all available cars
        CarDisplay display = new CarDisplay();
        display.showCars(cars);

        // Get user input with validation
        InputHandler inputHandler = new InputHandler();
        UserInput input = inputHandler.getUserInput();

        // Additional validation (defense in depth)
        if (!InputValidator.validatePassengers(input.getPassengers()) ||
            !InputValidator.validateDays(input.getDays()) ||
            !InputValidator.validateMileage(input.getMileage())) {
            System.out.println("Invalid input detected. Please restart the application.");
            return;
        }

        // Filter cars by passenger capacity (Authorization)
        CarFilterService filterService = new CarFilterService();
        List<CarWithDetails> eligibleCars = filterService.filterByPassengers(cars, input);
        
        if (eligibleCars.isEmpty()) {
            ResultDisplay resultDisplay = new ResultDisplay();
            resultDisplay.showResults(eligibleCars, input.getPassengers(), 
                                      input.getDays(), input.getMileage());
            return;
        }

        // Calculate costs for all eligible cars
        CostCalculator calculator = new CostCalculator();
        calculator.calculateAllCosts(eligibleCars, input);

        // Find the BEST car(s) (lowest cost, then best comfort)
        List<CarWithDetails> bestCars = filterService.findBestCars(eligibleCars);

        // Display final results (only the best car(s))
        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplay.showResults(bestCars, input.getPassengers(), 
                                  input.getDays(), input.getMileage());
    }
}