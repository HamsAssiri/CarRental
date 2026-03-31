package service;

import model.CarWithDetails;
import model.ICar;
import model.HierarchyLoader;
import model.HierarchyData;
import model.UserInput;

import java.util.ArrayList;
import java.util.List;

//Filters by cost then comfort level as required 
public class CarFilterService {
    
    private final HierarchyLoader hierarchyLoader;
    
    public CarFilterService() {
        this.hierarchyLoader = new HierarchyLoader();
    }
    
    //Filter cars by passenger capacity so ONLY cars that can accommodate the number of passengers
    public List<CarWithDetails> filterByPassengers(List<ICar> cars, UserInput input) {
        List<CarWithDetails> eligibleCars = new ArrayList<>();
        
        for (ICar car : cars) {
            HierarchyData hierarchyData = hierarchyLoader.getHierarchyData(car.getHierarchy());
            
            // Fail safe: if hierarchy not found skip this car
            if (hierarchyData == null) {
                System.err.println("Warning: Unknown hierarchy '" + car.getHierarchy() + 
                                 "' for car " + car.getName());
                continue;
            }
            
            // Authorization: only cars that can fit passengers
            if (hierarchyData.getMaxPassengers() >= input.getPassengers()) {
                eligibleCars.add(new CarWithDetails(car, hierarchyData));
            }
        }
        
        return eligibleCars;
    }
    
    /*Find the BEST car(s) based on:
     - 1. Lowest total cost (primary)
     - 2. Best comfort level (secondary tiebreaker)
     - Returns ONLY the best car(s) - if multiple have same cost AND comfort, return all
     */
    public List<CarWithDetails> findBestCars(List<CarWithDetails> cars) {
        if (cars == null || cars.isEmpty()) {
            return new ArrayList<>();
        }
        
        // sort to find the best combination
        cars.sort((car1, car2) -> {
            // Compare by total cost first
            int costCompare = Double.compare(car1.getTotalCost(), car2.getTotalCost());
            if (costCompare != 0) {
                return costCompare;
            }
            // If costs are equal, compare by comfort (higher comfort first)
            return compareComfortLevel(car2.getComfortLevel(), car1.getComfortLevel());
        });
        
        // The first car after sorting is the best candidate
        CarWithDetails bestCar = cars.get(0);
        double bestCost = bestCar.getTotalCost();
        String bestComfort = bestCar.getComfortLevel();
        
        // Find all cars that match the best criteria
        List<CarWithDetails> bestCars = new ArrayList<>();
        for (CarWithDetails car : cars) {
            // If car has the same lowest cost AND same comfort level
            if (Math.abs(car.getTotalCost() - bestCost) < 0.01 && 
                car.getComfortLevel().equals(bestComfort)) {
                bestCars.add(car);
            } else {
                // Once we find a car that doesn't match, stop (since sorted)
                break;
            }
        }
        
        return bestCars;
    }
    
    /* Compare comfort levels: Good > Medium > Poor
     - Returns positive if comfort1 is better than comfort2
     */
    private int compareComfortLevel(String comfort1, String comfort2) {
        int value1 = getComfortValue(comfort1);
        int value2 = getComfortValue(comfort2);
        return Integer.compare(value1, value2);
    }
    
    private int getComfortValue(String comfort) {
        switch (comfort) {
            case "Good": return 3;
            case "Medium": return 2;
            case "Poor": return 1;
            default: return 0;
        }
    }
}