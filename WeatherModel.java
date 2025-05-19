package com.weather.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WeatherModel implements WeatherObservable {
    private WeatherDataRepository repository;
    private UserPreferences userPreferences;
    private List<WeatherObserver> observers = new ArrayList<>();
    private String selectedCity;
    private LocalDate selectedDate;
    
    public WeatherModel(WeatherDataRepository repository, UserPreferences userPreferences) {
        this.repository = repository;
        this.userPreferences = userPreferences;
    }
    
    public WeatherDataRepository getRepository() { return repository; }
    public UserPreferences getUserPreferences() { return userPreferences; }
    public String getSelectedCity() { return selectedCity; }
    public LocalDate getSelectedDate() { return selectedDate; }
    
    @Override
    public void addObserver(WeatherObserver observer) {
        observers.add(observer);
    }
    
    @Override
    public void removeObserver(WeatherObserver observer) {
        observers.remove(observer);
    }
    
    @Override
    public void notifyObservers() {
        for (WeatherObserver observer : observers) {
            observer.update(this);
        }
    }
    
    public void setPreferredTemperatureUnit(TemperatureUnit unit) {
        userPreferences.setPreferredUnit(unit);
        notifyObservers();
    }
    
    public void setSelectedCity(String cityName) {
        this.selectedCity = cityName;
        notifyObservers();
    }
    
    public void setSelectedDate(LocalDate date) {
        this.selectedDate = date;
        notifyObservers();
    }
    
    public void addTrackedCity(String cityName) {
        userPreferences.getTrackedCities().add(cityName);
        notifyObservers();
    }
    
    public void removeTrackedCity(String cityName) {
        userPreferences.getTrackedCities().remove(cityName);
        notifyObservers();
    }
}