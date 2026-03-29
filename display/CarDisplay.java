package display;

import java.util.List;
import model.ICar;

public class CarDisplay {

    public void showCars(List<ICar> cars) {
        System.out.println("\nAvailable Cars:");
        System.out.println("──────────────────────────────────────────────");
        for (int i = 0; i < cars.size(); i++) {
            ICar car = cars.get(i);
            System.out.printf("  %d. %s%n", i + 1, car.getName());
            System.out.printf("     Type: %-15s Class: %-15s MPG: %s%n",
                car.getCategory(), car.getHierarchy(), car.getGas());
            System.out.println("──────────────────────────────────────────────");
        }
    }
}