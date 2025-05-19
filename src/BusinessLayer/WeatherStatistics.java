package src.BusinessLayer;

import java.time.LocalDate;
import java.util.Comparator;

public class WeatherStatistics {
    private WeatherDataRepository repository;
    
    public WeatherStatistics(WeatherDataRepository repository) {
        this.repository = repository;
    }
    
    public City getCityWithHighestAverageTemperature() {
        return repository.getAllCities().stream()
                .max(Comparator.comparingDouble(this::calculateAverageTemperature))
                .orElse(null);
    }
    
    public City getCityWithLowestAverageTemperature() {
        return repository.getAllCities().stream()
                .min(Comparator.comparingDouble(this::calculateAverageTemperature))
                .orElse(null);
    }
    
    public City getCityWithLowestTemperatureInJanuary() {
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 1, 31);
        
        return repository.getAllCities().stream()
                .min(Comparator.comparingDouble(city -> getMinTemperatureInRange(city, startDate, endDate)))
                .orElse(null);
    }
    
    public City getCityWithHighestAverageHumidityInMay() {
        LocalDate startDate = LocalDate.of(2025, 5, 1);
        LocalDate endDate = LocalDate.of(2025, 5, 31);
        
        return repository.getAllCities().stream()
                .max(Comparator.comparingDouble(city -> getAverageHumidityInRange(city, startDate, endDate)))
                .orElse(null);
    }
    
    public City getCityWithHighestAverageWindSpeedInApril() {
        LocalDate startDate = LocalDate.of(2025, 4, 1);
        LocalDate endDate = LocalDate.of(2025, 4, 30);
        
        return repository.getAllCities().stream()
                .max(Comparator.comparingDouble(city -> getAverageWindSpeedInRange(city, startDate, endDate)))
                .orElse(null);
    }
    
    public double calculateAverageTemperature(City city) {
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 5, 31);
        
        return city.getWeatherData().stream()
                .filter(data -> !data.getDate().isBefore(startDate) && !data.getDate().isAfter(endDate))
                .mapToDouble(WeatherData::getTemperature)
                .average()
                .orElse(0);
    }
    
    public double calculateAverageTemperature(City city, TemperatureUnit unit) {
        double avgCelsius = calculateAverageTemperature(city);
        if (unit == TemperatureUnit.CELSIUS) {
            return avgCelsius;
        } else {
            return avgCelsius * 9/5 + 32;
        }
    }
    
    private double getMinTemperatureInRange(City city, LocalDate startDate, LocalDate endDate) {
        return city.getWeatherData().stream()
                .filter(data -> !data.getDate().isBefore(startDate) && !data.getDate().isAfter(endDate))
                .mapToDouble(WeatherData::getTemperature)
                .min()
                .orElse(Double.MAX_VALUE);
    }
    
    public double getMinTemperatureInJanuary(City city, TemperatureUnit unit) {
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 1, 31);
        
        double minTemp = getMinTemperatureInRange(city, startDate, endDate);
        
        if (unit == TemperatureUnit.CELSIUS) {
            return minTemp;
        } else {
            return minTemp * 9/5 + 32;
        }
    }
    
    private double getAverageHumidityInRange(City city, LocalDate startDate, LocalDate endDate) {
        return city.getWeatherData().stream()
                .filter(data -> !data.getDate().isBefore(startDate) && !data.getDate().isAfter(endDate))
                .mapToDouble(WeatherData::getHumidity)
                .average()
                .orElse(0);
    }
    
    public double getAverageHumidityInMay(City city) {
        LocalDate startDate = LocalDate.of(2025, 5, 1);
        LocalDate endDate = LocalDate.of(2025, 5, 31);
        
        return getAverageHumidityInRange(city, startDate, endDate);
    }
    
    private double getAverageWindSpeedInRange(City city, LocalDate startDate, LocalDate endDate) {
        return city.getWeatherData().stream()
                .filter(data -> !data.getDate().isBefore(startDate) && !data.getDate().isAfter(endDate))
                .mapToDouble(WeatherData::getWindSpeed)
                .average()
                .orElse(0);
    }
    
    public double getAverageWindSpeedInApril(City city) {
        LocalDate startDate = LocalDate.of(2025, 4, 1);
        LocalDate endDate = LocalDate.of(2025, 4, 30);
        
        return getAverageWindSpeedInRange(city, startDate, endDate);
    }
}