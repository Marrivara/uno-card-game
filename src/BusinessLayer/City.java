package src.BusinessLayer;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class City {
    private String name;
    private List<WeatherData> weatherDataList;
    
    public City(String name, List<WeatherData> weatherDataList) {
        this.name = name;
        this.weatherDataList = weatherDataList;
    }
    
    public String getName() { return name; }
    public List<WeatherData> getWeatherData() { return weatherDataList; }
    
    public WeatherData getWeatherForDate(LocalDate date) {
        return weatherDataList.stream()
                .filter(data -> data.getDate().equals(date))
                .findFirst()
                .orElse(null);
    }
    
    public WeatherData getCurrentWeather() {
        return weatherDataList.stream()
                .max(Comparator.comparing(WeatherData::getDate))
                .orElse(null);
    }
}