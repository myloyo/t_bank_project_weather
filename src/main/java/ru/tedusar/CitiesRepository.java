package ru.tedusar;

import java.util.*;

import static ru.tedusar.WeatherService.*;

public class CitiesRepository {
    public static Map<String, Integer> cities = new HashMap<>();
    public static Map<String, Integer> setCities() {
        cities.put("Саратов", 0);
        cities.put("Москва", 0);
        cities.put("Казань", 0);
        cities.put("Санкт-Петербург", 0);
        cities.put("Екатеринбург", 0);
        cities.put("Пенза", 0);
        cities.put("Балаково", 0);
        cities.put("Балашов", 0);
        cities.put("Норильск", 0);
        cities.put("Ростов", 0);
        setWeather();
        return cities;
    }
}
