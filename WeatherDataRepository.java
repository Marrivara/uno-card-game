package com.weather.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherDataRepository {
    private List<City> cities = new ArrayList<>();
    private List<WeatherData> allWeatherData = new ArrayList<>();
    
    public void loadDataFromCSV(String filePath) throws IOException {
        allWeatherData.clear();
        cities.clear();
        Map<String, List<WeatherData>> cityMap = new HashMap<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Skip header line
            String line = reader.readLine();
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    String cityName = parts[0];
                    LocalDate date = LocalDate.parse(parts[1]);
                    double temperature = Double.parseDouble(parts[2]);
                    int humidity = Integer.parseInt(parts[3]);
                    double windSpeed = Double.parseDouble(parts[4]);
                    String condition = parts[5];
                    
                    WeatherData data = new WeatherData(cityName, date, temperature, humidity, windSpeed, condition);
                    allWeatherData.add(data);
                    
                    cityMap.computeIfAbsent(cityName, k -> new ArrayList<>()).add(data);
                }
            }
        }
        
        for (Map.Entry<String, List<WeatherData>> entry : cityMap.entrySet()) {
            City city = new City(entry.getKey(), entry.getValue());
            cities.add(city);
        }
    }
    
    public List<City> getAllCities() {
        return new ArrayList<>(cities);
    }
    
    public City getCityByName(String name) {
        return cities.stream()
                .filter(city -> city.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}