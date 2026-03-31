package model;

/*This stores the rental rates, passenger limits, 
and comfort levels for each hierarchy*/
public class HierarchyData {
    private final String hierarchy;
    private final double dailyRate;
    private final int maxPassengers;
    private final String comfortLevel;
    
    public HierarchyData(String hierarchy, double dailyRate, int maxPassengers, String comfortLevel) {
        this.hierarchy = hierarchy;
        this.dailyRate = dailyRate;
        this.maxPassengers = maxPassengers;
        this.comfortLevel = comfortLevel;
    }
    
    public String getHierarchy() {
        return hierarchy;
    }
    
    public double getDailyRate() {
        return dailyRate;
    }
    
    public int getMaxPassengers() {
        return maxPassengers;
    }
    
    public String getComfortLevel() {
        return comfortLevel;
    }
}
