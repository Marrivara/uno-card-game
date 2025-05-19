package com.weather.model;

import java.time.LocalDate;

public class WeatherData {
    private String city;
    private LocalDate date;
    private double temperature;
    private int humidity;
    private double windSpeed;
    private String condition;

    public WeatherData(String city, LocalDate date, double temperature, int humidity, double windSpeed, String condition) {
        this.city = city;
        this.date = date;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.condition = condition;
    }

    public String getCity() { return city; }
    public LocalDate getDate() { return date; }
    public double getTemperature() { return temperature; }
    public int getHumidity() { return humidity; }
    public double getWindSpeed() { return windSpeed; }
    public String getCondition() { return condition; }

    public double getTemperature(TemperatureUnit unit) {
        if (unit == TemperatureUnit.CELSIUS) {
            return temperature;
        } else {
            return celsiusToFahrenheit(temperature);
        }
    }
    
    private double celsiusToFahrenheitConverter(double celsius) {
        return celsius * 9/5 + 32;
    }
}