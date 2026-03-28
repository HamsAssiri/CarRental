import model.CarLoader;
import model.ICar;
import java.util.List;

public class CarRentalApp {

    public static void main(String[] args) {
        // Create loader instance
        CarLoader loader = new CarLoader();

        // Load cars from JSON
        List<ICar> cars = loader.loadCars();

        // Check if cars loaded successfully
        if (cars.isEmpty()) {
            System.out.println("Failed to load car data. Please check data/cars.json file.");
            return;
        }

        // for debugging purposes, print all cars laoded from file
        System.out.println("Available cars:");
        for (ICar car : cars) {
            System.out.println("  " + car.getName() +
                    " (" + car.getCategory() +
                    ", " + car.getHierarchy() +
                    ", " + car.getGas() + " MPG)");
        }
    }
}