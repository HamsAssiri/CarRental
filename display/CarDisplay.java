package display;

import java.util.List;
import model.ICar;

public class CarDisplay {

    public void showCars(List<ICar> cars) {
        System.out.println();
        System.out.println("+-----------------------------------------------------+");
        System.out.println("|              AVAILABLE CARS IN OUR FLEET            |");
        System.out.println("+-----------------------------------------------------+");
        
        for (int i = 0; i < cars.size(); i++) {
            ICar car = cars.get(i);
            System.out.printf("| %2d | %-30s | %-10s | %-8s | %3d MPG |%n", 
                i + 1, 
                truncate(car.getName(), 30), 
                car.getCategory(), 
                car.getHierarchy(), 
                car.getGas());
        }
        System.out.println("+-----------------------------------------------------+");
        System.out.println();
    }
    
    private String truncate(String str, int length) {
        if (str.length() <= length) {
            return str;
        }
        return str.substring(0, length - 3) + "...";
    }
}