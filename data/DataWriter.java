package data;

import java.io.FileWriter;
import java.io.IOException;

// This class is a utility to create the cars.json file with sample data.
public class DataWriter {

    public static void main(String[] args) {
        String jsonContent = """
                [
                    {
                        "name": "2026 Honda CR-V",
                        "category": "SUV",
                        "hierarchy": "Standard",
                        "gas": 28
                    },
                    {
                        "name": "2026 Chevrolet Silverado 1500",
                        "category": "Truck",
                        "hierarchy": "Standard",
                        "gas": 19
                    },
                    {
                        "name": "2026 Kia Sorento Hybrid",
                        "category": "Hybrid",
                        "hierarchy": "Intermediate",
                        "gas": 34
                    },
                    {
                        "name": "2026 Chrysler Pacifica",
                        "category": "Van/Minivan",
                        "hierarchy": "Van",
                        "gas": 20
                    },
                    {
                        "name": "2024 Toyota GR86",
                        "category": "Coupe",
                        "hierarchy": "Economy",
                        "gas": 25
                    },
                    {
                        "name": "2026 Hyundai Kona SEL Premium",
                        "category": "Crossover",
                        "hierarchy": "Standard",
                        "gas": 26
                    },
                    {
                        "name": "2026 Mercedes-Benz Mercedes-AMG E-Class",
                        "category": "Sedan",
                        "hierarchy": "Intermediate",
                        "gas": 24
                    },
                    {
                        "name": "2025 Kia Carnival LX",
                        "category": "Van/Minivan",
                        "hierarchy": "Van",
                        "gas": 21
                    },
                    {
                        "name": "2026 Ford Mustang Ecoboost",
                        "category": "Coupe",
                        "hierarchy": "Economy",
                        "gas": 26
                    }
                ]
                """;

        try (FileWriter writer = new FileWriter("data/cars.json")) {
            writer.write(jsonContent);
            System.out.println("cars.json created successfully in data folder");
        } catch (IOException e) {
            System.err.println("Error creating cars.json: " + e.getMessage());
        }
    }
}