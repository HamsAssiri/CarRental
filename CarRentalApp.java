import java.util.Locale;

import javax.swing.SwingUtilities;

public class CarRentalApp {

    public static void main(String[] args) {
        // Force English locale for number formatting to ensure proper display
        Locale.setDefault(Locale.ENGLISH);
        System.setProperty("user.country", "US");
        System.setProperty("user.language", "en");
        
        // Launch GUI directly
        SwingUtilities.invokeLater(() -> {
            gui.CarRentalGUI.main(new String[0]);
        });
    }
}