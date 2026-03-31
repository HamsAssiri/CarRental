package model;

import java.util.HashMap;
import java.util.Map;

//Loads hierarchy data and provides lookup functionality
public class HierarchyLoader {
    
    private final Map<String, HierarchyData> hierarchyMap;
    
    public HierarchyLoader() {
        hierarchyMap = new HashMap<>();
        loadHierarchies();
    }
    
    private void loadHierarchies() {
        // Based on project specifications
        hierarchyMap.put("Economy", new HierarchyData("Economy", 45.0, 4, "Poor"));
        hierarchyMap.put("Intermediate", new HierarchyData("Intermediate", 50.0, 4, "Medium"));
        hierarchyMap.put("Standard", new HierarchyData("Standard", 55.0, 5, "Good"));
        hierarchyMap.put("Van", new HierarchyData("Van", 70.0, 7, "Medium"));
    }
    
    public HierarchyData getHierarchyData(String hierarchy) {
        return hierarchyMap.get(hierarchy);
    }
    
    public boolean isValidHierarchy(String hierarchy) {
        return hierarchyMap.containsKey(hierarchy);
    }
}