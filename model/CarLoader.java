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
            String jsonContent = Files.readString(Paths.get("data/cars.json"));

            // استخرج كل سيارة بين { و }
            String[] carEntries = jsonContent.split("\\{");

            for (String entry : carEntries) {
                // Minimize trust: تجاهل أي entry مو فيه name
                if (!entry.contains("name")) continue;

                String name      = extractValue(entry, "name");
                String category  = extractValue(entry, "category");
                String hierarchy = extractValue(entry, "hierarchy");
                String gasStr    = extractValue(entry, "gas");

                // Minimize trust: تحقق من القيم قبل ما تضيف
                int gas = gasStr.isEmpty() ? 0 : Integer.parseInt(gasStr.trim());

                // Fail Safe: ما تضيف سيارة ناقصة بيانات
                if (!name.isEmpty() && !category.isEmpty() && !hierarchy.isEmpty() && gas > 0) {
                    cars.add(new Car(name, category, hierarchy, gas));
                }
            }

        } catch (IOException e) {
            // Fail Safe: ارجع قائمة فاضية عند الخطأ
            System.err.println("Error loading cars: " + e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            // Fail Safe: ارجع قائمة فاضية عند أي خطأ ثاني
            System.err.println("Error parsing car data: " + e.getMessage());
            return new ArrayList<>();
        }

        return cars;
    }

    // Least Privilege: دالة خاصة لاستخراج القيم
    private String extractValue(String entry, String key) {
        String search = "\"" + key + "\"";
        int keyIndex = entry.indexOf(search);
        if (keyIndex == -1) return "";

        int colonIndex = entry.indexOf(":", keyIndex);
        if (colonIndex == -1) return "";

        String rest = entry.substring(colonIndex + 1).trim();

        // قيمة نصية
        if (rest.startsWith("\"")) {
            int start = rest.indexOf("\"") + 1;
            int end   = rest.indexOf("\"", start);
            return rest.substring(start, end);
        }

        // قيمة رقمية
        StringBuilder num = new StringBuilder();
        for (char c : rest.toCharArray()) {
            if (Character.isDigit(c)) num.append(c);
            else if (!num.isEmpty()) break;
        }
        return num.toString();
    }
}