package model;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CarLoader {

    // Public method that returns a list of cars by reading from the cars.json file
    public List<ICar> loadCars() {
        List<ICar> cars = new ArrayList<>();

        try {
            // Read the entire file as one string
            String jsonContent = Files.readString(Paths.get("data/cars.json"));

            // Remove all whitespace (spaces, newlines, tabs) to make parsing easier
            jsonContent = jsonContent.replaceAll("\\s+", "");

            // Remove the opening and closing square brackets [ and ]
            jsonContent = jsonContent.substring(1, jsonContent.length() - 1);

            // Split by "},{" to separate each car entry
            String[] carEntries = jsonContent.split("\\},\\{");

            // Loop through each car entry and parse it
            for (String entry : carEntries) {
                // Remove any remaining { or } characters
                entry = entry.replace("{", "").replace("}", "");

                // Split by comma to get individual field-value pairs
                String[] fields = entry.split(",");

                // Variables to store extracted values
                String name = "";
                String category = "";
                String hierarchy = "";
                int gas = 0;

                // Parse each field
                for (String field : fields) {
                    // Split by colon
                    String[] keyValue = field.split(":");

                    // Extract key (remove quotes)
                    String key = keyValue[0].replace("\"", "");

                    // Extract value (remove quotes)
                    String value = keyValue[1].replace("\"", "");

                    // Assign to correct variable
                    if (key.equals("name")) {
                        name = value;
                    } else if (key.equals("category")) {
                        category = value;
                    } else if (key.equals("hierarchy")) {
                        hierarchy = value;
                    } else if (key.equals("gas")) {
                        gas = Integer.parseInt(value);
                    }
                }

                // Create and add car object
                Car car = new Car(name, category, hierarchy, gas);
                cars.add(car);
            }

        } catch (IOException e) {
            System.err.println("Error loading cars: " + e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Error parsing car data: " + e.getMessage());
            return new ArrayList<>();
        }

        return cars;
    }
}
