package com.weather.view;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.weather.model.City;
import com.weather.model.TemperatureUnit;
import com.weather.model.WeatherModel;
import com.weather.model.WeatherObservable;
import com.weather.model.WeatherObserver;
import com.weather.model.WeatherStatistics;

public class StatisticsPanel extends JPanel implements WeatherObserver {
    private WeatherModel model;
    private WeatherStatistics statistics;
    
    private JLabel highestAvgTempLabel;
    private JLabel lowestAvgTempLabel;
    private JLabel lowestTempJanLabel;
    private JLabel highestAvgHumidityMayLabel;
    private JLabel highestAvgWindAprilLabel;
    
    public StatisticsPanel(WeatherModel model) {
        this.model = model;
        this.statistics = new WeatherStatistics(model.getRepository());
        
        // Initialize UI components
        initComponents();
        
        // Register as observer
        model.addObserver(this);
    }
    
    private void initComponents() {
        setBorder(BorderFactory.createTitledBorder("Weather Statistics (Jan-May 2025)"));
        setLayout(new GridLayout(5, 1, 5, 5));
        
        highestAvgTempLabel = new JLabel("Highest Average Temperature: Calculating...");
        lowestAvgTempLabel = new JLabel("Lowest Average Temperature: Calculating...");
        lowestTempJanLabel = new JLabel("Lowest Temperature in January: Calculating...");
        highestAvgHumidityMayLabel = new JLabel("Highest Average Humidity in May: Calculating...");
        highestAvgWindAprilLabel = new JLabel("Highest Average Wind Speed in April: Calculating...");
        
        add(highestAvgTempLabel);
        add(lowestAvgTempLabel);
        add(lowestTempJanLabel);
        add(highestAvgHumidityMayLabel);
        add(highestAvgWindAprilLabel);
        
        updateStatistics();
    }
    
    private void updateStatistics() {
        TemperatureUnit unit = model.getUserPreferences().getPreferredUnit();
        String unitSymbol = (unit == TemperatureUnit.CELSIUS) ? "°C" : "°F";
        
        City highestAvgTemp = statistics.getCityWithHighestAverageTemperature();
        if (highestAvgTemp != null) {
            double avgTemp = statistics.calculateAverageTemperature(highestAvgTemp, unit);
            highestAvgTempLabel.setText("Highest Avg Temp: " + highestAvgTemp.getName() + 
                    " (" + String.format("%.1f", avgTemp) + " " + unitSymbol + ")");
        }
        
        City lowestAvgTemp = statistics.getCityWithLowestAverageTemperature();
        if (lowestAvgTemp != null) {
            double avgTemp = statistics.calculateAverageTemperature(lowestAvgTemp, unit);
            lowestAvgTempLabel.setText("Lowest Avg Temp: " + lowestAvgTemp.getName() + 
                    " (" + String.format("%.1f", avgTemp) + " " + unitSymbol + ")");
        }
        
        City lowestTempJan = statistics.getCityWithLowestTemperatureInJanuary();
        if (lowestTempJan != null) {
            double minTemp = statistics.getMinTemperatureInJanuary(lowestTempJan, unit);
            lowestTempJanLabel.setText("Lowest Temp in Jan: " + lowestTempJan.getName() + 
                    " (" + String.format("%.1f", minTemp) + " " + unitSymbol + ")");
        }
        
        City highestHumidityMay = statistics.getCityWithHighestAverageHumidityInMay();
        if (highestHumidityMay != null) {
            double avgHumidity = statistics.getAverageHumidityInMay(highestHumidityMay);
            highestAvgHumidityMayLabel.setText("Highest Avg Humidity in May: " + 
                    highestHumidityMay.getName() + " (" + String.format("%.1f", avgHumidity) + "%)");
        }
        
        City highestWindApril = statistics.getCityWithHighestAverageWindSpeedInApril();
        if (highestWindApril != null) {
            double avgWind = statistics.getAverageWindSpeedInApril(highestWindApril);
            highestAvgWindAprilLabel.setText("Highest Avg Wind in April: " + 
                    highestWindApril.getName() + " (" + String.format("%.1f", avgWind) + " km/h)");
        }
    }
    
    @Override
    public void update(WeatherObservable observable) {
        updateStatistics();
    }
}