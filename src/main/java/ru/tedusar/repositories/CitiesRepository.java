package ru.tedusar.repositories;

import ru.tedusar.classes.WeatherClass;

import java.util.*;

public class CitiesRepository {
    private final Map<String, WeatherClass> cities = new HashMap<>();

    public CitiesRepository() {
        List<String> defaultCities = Arrays.asList(
                "Саратов", "Москва", "Казань", "Санкт-Петербург",
                "Екатеринбург", "Пенза", "Балаково", "Балашов", "Норильск", "Ростов"
        );
        for (String city : defaultCities) {
            cities.put(city, null);
        }
    }

    public void addCity(String cityName, WeatherClass weather) {
        cities.put(cityName, weather);
    }

    public WeatherClass getCity(String cityName) {
        return cities.get(cityName);
    }

    public boolean containsCity(String cityName) {
        return cities.containsKey(cityName);
    }

    public Set<String> getAllCities() {
        return cities.keySet();
    }

    public void updateWeather(String cityName, WeatherClass weather) {
        if (cities.containsKey(cityName)) {
            cities.put(cityName, weather);
        }
    }
}
