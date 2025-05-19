package src.BusinessLayer;

import java.time.LocalDate;

public class WeatherController {
    private WeatherModel model;
    
    public WeatherController(WeatherModel model) {
        this.model = model;
    }
    
    public void selectCity(String cityName) {
        model.setSelectedCity(cityName);
    }
    
    public void selectDate(LocalDate date) {
        model.setSelectedDate(date);
    }
    
    public void setTemperatureUnit(TemperatureUnit unit) {
        model.setPreferredTemperatureUnit(unit);
    }
    
    public void addTrackedCity(String cityName) {
        model.addTrackedCity(cityName);
    }
    
    public void removeTrackedCity(String cityName) {
        model.removeTrackedCity(cityName);
    }
}