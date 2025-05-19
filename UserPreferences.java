package com.weather.model;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class UserPreferences {
    private TemperatureUnit preferredUnit = TemperatureUnit.CELSIUS;
    private Set<String> trackedCities = new HashSet<>();
    
    public UserPreferences() {
        trackedCities.add("İzmir");
        trackedCities.add("Antalya");
    }
    
    public TemperatureUnit getPreferredUnit() { return preferredUnit; }
    public void setPreferredUnit(TemperatureUnit unit) { this.preferredUnit = unit; }
    public Set<String> getTrackedCities() { return trackedCities; }
    
    public void saveToFile(String filePath) throws IOException {
        Properties properties = new Properties();
        
        properties.setProperty("unit", preferredUnit.name());
        
        StringBuilder citiesStr = new StringBuilder();
        for (String city : trackedCities) {
            if (citiesStr.length() > 0) {
                citiesStr.append(",");
            }
            citiesStr.append(city);
        }
        properties.setProperty("trackedCities", citiesStr.toString());
        
        try (FileWriter writer = new FileWriter(filePath)) {
            properties.store(writer, "Weather App User Preferences");
        }
    }
    
    public static UserPreferences loadFromFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            return new UserPreferences();
        }
        
        Properties properties = new Properties();
        try (FileReader reader = new FileReader(file)) {
            properties.load(reader);
        }
        
        UserPreferences preferences = new UserPreferences();
        
        String unitStr = properties.getProperty("unit");
        if (unitStr != null) {
            try {
                preferences.setPreferredUnit(TemperatureUnit.valueOf(unitStr));
            } catch (IllegalArgumentException e) {
            }
        }
        
        String citiesStr = properties.getProperty("trackedCities");
        if (citiesStr != null && !citiesStr.isEmpty()) {
            preferences.trackedCities.clear();
            for (String city : citiesStr.split(",")) {
                preferences.trackedCities.add(city.trim());
            }
        }
        
        return preferences;
    }
}