import model.CarLoader;
import model.ICar;
import model.UserInput;
import util.InputHandler;
import util.Messages;
import display.CarDisplay;          
import validation.InputValidator;   

import java.util.List;

public class CarRentalApp {

    public static void main(String[] args) {

        Messages.showWelcome();

        CarLoader loader = new CarLoader();
        List<ICar> cars = loader.loadCars();

        if (cars == null) {
            Messages.showFatalError();
            return;
        }

        // استخدم CarDisplay
        CarDisplay display = new CarDisplay();
        display.showCars(cars);

        InputHandler inputHandler = new InputHandler();
        UserInput input = inputHandler.getUserInput();

        // تحقق إضافي باستخدام InputValidator
        if (!InputValidator.validatePassengers(input.getPassengers()) ||
            !InputValidator.validateDays(input.getDays()) ||
            !InputValidator.validateMileage(input.getMileage())) {
            System.out.println("Invalid input detected.");
            return;
        }

        System.out.println("\n--- User Input Summary ---");
        System.out.println("Passengers: " + input.getPassengers());
        System.out.println("Days: " + input.getDays());
        System.out.println("Mileage: " + input.getMileage());
    }
}