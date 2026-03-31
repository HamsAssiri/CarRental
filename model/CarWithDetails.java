package model;

//Wrapper class that combines Car with its hierarchy details

public class CarWithDetails {
    private final ICar car;
    private final HierarchyData hierarchyData;
    private double totalCost;
    
    public CarWithDetails(ICar car, HierarchyData hierarchyData) {
        this.car = car;
        this.hierarchyData = hierarchyData;
        this.totalCost = 0;
    }
    
    public ICar getCar() {
        return car;
    }
    
    public HierarchyData getHierarchyData() {
        return hierarchyData;
    }
    
    public double getTotalCost() {
        return totalCost;
    }
    
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
    
    public String getName() {
        return car.getName();
    }
    
    public int getGas() {
        return car.getGas();
    }
    
    public String getCategory() {
        return car.getCategory();
    }
    
    public String getHierarchy() {
        return car.getHierarchy();
    }
    
    public double getDailyRate() {
        return hierarchyData.getDailyRate();
    }
    
    public int getMaxPassengers() {
        return hierarchyData.getMaxPassengers();
    }
    
    public String getComfortLevel() {
        return hierarchyData.getComfortLevel();
    }
}