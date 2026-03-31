package service;

import java.util.List;
import model.CarWithDetails;
import model.UserInput;

//All calculations are in this class
public class CostCalculator {
    
    private static final double GAS_PRICE = 2.25; // dollars per gallon
    
    /*
    Equations: 
    - Total Cost = (Rental Cost per day × Number of days) + (Mileage cost)
    - Mileage cost = (Miles driven / MPG) × $2.25

    a simple format: 
     Total Cost for a car = (Daily Rate × Days) + (Miles / MPG × Gas Price)
     */
    public double calculateTotalCost(CarWithDetails car, UserInput input) {
        double rentalCost = car.getDailyRate() * input.getDays();
        double gasCost = (input.getMileage() / (double) car.getGas()) * GAS_PRICE;
        return rentalCost + gasCost;
    }
    
    // Calculate and set total cost for all cars
    public void calculateAllCosts(List<CarWithDetails> cars, UserInput input) {
        for (CarWithDetails car : cars) {
            double cost = calculateTotalCost(car, input);
            car.setTotalCost(cost);
        }
    }
}
