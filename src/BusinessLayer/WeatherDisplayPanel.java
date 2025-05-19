package src.BusinessLayer;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class WeatherDisplayPanel extends JPanel implements WeatherObserver {
    private WeatherModel model;
    private JLabel titleLabel;
    private JLabel temperatureLabel;
    private JLabel humidityLabel;
    private JLabel windSpeedLabel;
    private JLabel conditionLabel;
    
    public WeatherDisplayPanel(WeatherModel model) {
        this.model = model;
        
        // Initialize UI components
        initComponents();
        
        // Register as observer
        model.addObserver(this);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Current Weather"));
        
        titleLabel = new JLabel("Please select a city and date");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);
        
        JPanel infoPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        temperatureLabel = new JLabel("Temperature: ");
        humidityLabel = new JLabel("Humidity: ");
        windSpeedLabel = new JLabel("Wind Speed: ");
        conditionLabel = new JLabel("Condition: ");
        
        infoPanel.add(temperatureLabel);
        infoPanel.add(humidityLabel);
        infoPanel.add(windSpeedLabel);
        infoPanel.add(conditionLabel);
        
        add(infoPanel, BorderLayout.CENTER);
        
        clearDisplay();
    }
    
    @Override
    public void update(WeatherObservable observable) {
        if (model.getSelectedCity() != null && model.getSelectedDate() != null) {
            City city = model.getRepository().getCityByName(model.getSelectedCity());
            if (city != null) {
                WeatherData data = city.getWeatherForDate(model.getSelectedDate());
                
                if (data != null) {
                    titleLabel.setText(data.getCity() + " - " + data.getDate());
                    
                    TemperatureUnit unit = model.getUserPreferences().getPreferredUnit();
                    double temp = data.getTemperature(unit);
                    String unitSymbol = (unit == TemperatureUnit.CELSIUS) ? "°C" : "°F";
                    temperatureLabel.setText("Temperature: " + String.format("%.1f", temp) + " " + unitSymbol);
                    
                    humidityLabel.setText("Humidity: " + data.getHumidity() + "%");
                    windSpeedLabel.setText("Wind Speed: " + data.getWindSpeed() + " km/h");
                    conditionLabel.setText("Condition: " + data.getCondition());
                } else {
                    titleLabel.setText(city.getName() + " - No data for " + model.getSelectedDate());
                    clearDisplay();
                }
            } else {
                clearDisplay();
            }
        } else {
            clearDisplay();
        }
    }
    
    private void clearDisplay() {
        if (model.getSelectedCity() == null || model.getSelectedDate() == null) {
            titleLabel.setText("Please select a city and date");
        }
        temperatureLabel.setText("Temperature: --");
        humidityLabel.setText("Humidity: --");
        windSpeedLabel.setText("Wind Speed: --");
        conditionLabel.setText("Condition: --");
    }
}
