package src.BusinessLayer;

import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


public class Main {
    public static void main(String[] args) {
        try {
            // Load weather data
            WeatherDataRepository repository = new WeatherDataRepository();
            repository.loadDataFromCSV("weather_data.csv");

            // Load user preferences
            UserPreferences preferences;
            try {
                preferences = UserPreferences.loadFromFile("preferences.properties");
            } catch (IOException e) {
                // If preferences file doesn't exist, create default preferences
                preferences = new UserPreferences();
            }

            // Create model
            final WeatherModel model = new WeatherModel(repository, preferences);

            // Create controller
            final WeatherController controller = new WeatherController(model);

            // Create and show the main frame
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    MainFrame frame = new MainFrame(model, controller);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                }
            });

            // Save preferences when application exits
            UserPreferences finalPreferences = preferences;
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    try {
                        finalPreferences.saveToFile("preferences.properties");
                    } catch (IOException e) {
                        System.err.println("Error saving preferences: " + e.getMessage());
                    }
                }
            });
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading weather data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
}