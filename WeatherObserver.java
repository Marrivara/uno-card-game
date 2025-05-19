package com.weather.model;

public interface WeatherObserver {
    void update(WeatherObservable observable);
}