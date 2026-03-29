package model;

public class Car implements ICar {
    // all fields are final, immutable object
    private final String name;
    private final String category;
    private final String hierarchy;
    private final int gas;

    // Package-private constructor (only accessible within the same package)
    // apllying the principle of least privilege
    Car(String name, String category, String hierarchy, int gas) {
        this.name = name;
        this.category = category;
        this.hierarchy = hierarchy;
        this.gas = gas;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public String getHierarchy() {
        return hierarchy;
    }

    @Override
    public int getGas() {
        return gas;
    }
    
}
