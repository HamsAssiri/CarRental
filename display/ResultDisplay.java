package display;

import model.CarWithDetails;
import java.util.List;

public class ResultDisplay {
    
    public void showResults(List<CarWithDetails> cars, int passengers, int days, int mileage) {
        if (cars == null || cars.isEmpty()) {
            System.out.println();
            System.out.println("+-----------------------------------------------------+");
            System.out.printf("|  NO CARS AVAILABLE FOR %d PASSENGERS                |%n", passengers);
            System.out.println("+-----------------------------------------------------+");
            return;
        }
        
        System.out.println();
        System.out.println("+-----------------------------------------------------+");
        System.out.println("|         RECOMMENDED CAR(S) FOR YOUR TRIP           |");
        System.out.println("+-----------------------------------------------------+");
        System.out.println();
        System.out.println("  Trip Details:");
        System.out.println("  ---------------------------------------------------");
        System.out.println("    Passengers : " + passengers);
        System.out.println("    Rental Days: " + days);
        System.out.println("    Mileage    : " + mileage + " miles");
        System.out.println("    Gas Price  : $2.25 per gallon");
        System.out.println("  ---------------------------------------------------");
        System.out.println();
        
        if (cars.size() == 1) {
            System.out.println("  BEST CHOICE:");
            System.out.println("  ---------------------------------------------------");
            displayCar(cars.get(0));
        } else {
            System.out.println("  BEST CHOICES (" + cars.size() + " cars with same cost and comfort):");
            System.out.println("  ---------------------------------------------------");
            for (int i = 0; i < cars.size(); i++) {
                System.out.printf("  [%d] ", i + 1);
                displayCar(cars.get(i));
                if (i < cars.size() - 1) {
                    System.out.println("  ---------------------------------------------------");
                }
            }
        }
        
        System.out.println();
        System.out.println("+-----------------------------------------------------+");
        System.out.println("|  These cars have the lowest total cost and best   |");
        System.out.println("|  comfort level for your trip.                      |");
        System.out.println("+-----------------------------------------------------+");
    }
    
    private void displayCar(CarWithDetails car) {
        System.out.printf("    %s%n", car.getName());
        System.out.printf("      Category     : %-15s | Class : %-12s%n", 
            car.getCategory(), car.getHierarchy());
        System.out.printf("      Max Passengers: %d%n", car.getMaxPassengers());
        System.out.printf("      Fuel Efficiency: %d MPG%n", car.getGas());
        System.out.printf("      Comfort Level : %s%n", car.getComfortLevel());
        System.out.printf("      Daily Rate    : $%.2f%n", car.getDailyRate());
        System.out.printf("      TOTAL COST    : $%.2f%n", car.getTotalCost());
    }
    
    public void showError(String message) {
        System.out.println();
        System.out.println("+-----------------------------------------------------+");
        System.out.printf("|  ERROR: %-43s |%n", message);
        System.out.println("+-----------------------------------------------------+");
    }
}