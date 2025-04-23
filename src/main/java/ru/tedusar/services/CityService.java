package ru.tedusar.services;

import ru.tedusar.classes.WeatherClass;
import ru.tedusar.repositories.CitiesRepository;

import java.util.Set;

public class CityService {
    private final CitiesRepository repo;

    public CityService() {
        this.repo = new CitiesRepository();
    }
    public void addCity(String cityName, WeatherClass weather) {
        repo.addCity(cityName, weather);
    }

    public WeatherClass getCity(String cityName) {
        return repo.getCity(cityName);
    }

    public boolean containsCity(String cityName) {
        return repo.containsCity(cityName);
    }

    public Set<String> getAllCities() {
        return repo.getAllCities();
    }

    public void updateWeather(String cityName, WeatherClass weather) {
        repo.updateWeather(cityName, weather);
    }

}
