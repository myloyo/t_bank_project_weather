package ru.tedusar;

import ru.tedusar.Services.WeatherService;

import java.util.*;

public class CitiesRepository {
    public static Map<String, WeatherClass> cities = new HashMap<>();

    public static Map<String, WeatherClass> setCities() {
        List<String> cityList = Arrays.asList("Саратов", "Москва", "Казань", "Санкт-Петербург",
                "Екатеринбург", "Пенза", "Балаково", "Балашов", "Норильск", "Ростов");

        for (String city : cityList) {
            cities.put(city, null); // Временно null, заполнится в setWeather()
        }

        WeatherService.setWeather();
        return cities;
    }
}
