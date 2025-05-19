package src.BusinessLayer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class TrackedCitiesPanel extends JPanel implements WeatherObserver {
    private WeatherModel model;
    private WeatherController controller;
    private JPanel citiesPanel;
    
    public TrackedCitiesPanel(WeatherModel model, WeatherController controller) {
        this.model = model;
        this.controller = controller;
        
        // Initialize UI components
        initComponents();
        
        // Register as observer
        model.addObserver(this);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Tracked Cities"));
        
        citiesPanel = new JPanel();
        citiesPanel.setLayout(new BoxLayout(citiesPanel, BoxLayout.Y_AXIS));
        
        add(citiesPanel, BorderLayout.CENTER);
        
        updateTrackedCities();
    }
    
    private void updateTrackedCities() {
        citiesPanel.removeAll();
        
        for (String cityName : model.getUserPreferences().getTrackedCities()) {
            City city = model.getRepository().getCityByName(cityName);
            if (city != null) {
                WeatherData currentWeather = city.getCurrentWeather();
                if (currentWeather != null) {
                    JPanel cityPanel = new JPanel(new BorderLayout(5, 5));
                    cityPanel.setBorder(BorderFactory.createEtchedBorder());
                    cityPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
                    
                    // City name as title
                    JLabel nameLabel = new JLabel(city.getName());
                    nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
                    nameLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                    
                    // Weather information
                    JPanel infoPanel = new JPanel(new GridLayout(3, 1));
                    
                    TemperatureUnit unit = model.getUserPreferences().getPreferredUnit();
                    double temp = currentWeather.getTemperature(unit);
                    String unitSymbol = (unit == TemperatureUnit.CELSIUS) ? "°C" : "°F";
                    
                    JLabel tempLabel = new JLabel("Temperature: " + String.format("%.1f", temp) + " " + unitSymbol);
                    JLabel humidityLabel = new JLabel("Humidity: " + currentWeather.getHumidity() + "%");
                    JLabel conditionLabel = new JLabel("Condition: " + currentWeather.getCondition());
                    
                    infoPanel.add(tempLabel);
                    infoPanel.add(humidityLabel);
                    infoPanel.add(conditionLabel);
                    
                    // Untrack button
                    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                    JButton removeButton = new JButton("Untrack");
                    removeButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            controller.removeTrackedCity(cityName);
                        }
                    });
                    buttonPanel.add(removeButton);
                    
                    cityPanel.add(nameLabel, BorderLayout.NORTH);
                    cityPanel.add(infoPanel, BorderLayout.CENTER);
                    cityPanel.add(buttonPanel, BorderLayout.SOUTH);
                    
                    citiesPanel.add(cityPanel);
                    citiesPanel.add(Box.createVerticalStrut(10));
                }
            }
        }
        
        citiesPanel.revalidate();
        citiesPanel.repaint();
    }
    
    @Override
    public void update(WeatherObservable observable) {
        updateTrackedCities();
    }
}