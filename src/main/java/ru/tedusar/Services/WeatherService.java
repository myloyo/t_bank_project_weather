package ru.tedusar.Services;

import ru.tedusar.WeatherClass;

import java.util.*;

import static ru.tedusar.CitiesRepository.*;

public class WeatherService {
    private static final String[] DIRECTIONS = {"С", "СЗ", "З", "ЮЗ", "Ю", "ЮВ", "В", "СВ"};

    public static Map<String, WeatherClass> setWeather() {
        Random random = new Random();
        for (String city : cities.keySet()) {
            int temp = random.nextInt(41) - 10;
            int windSpeed = random.nextInt(15);
            String windDir = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
            int pressure = 720 + random.nextInt(41);
            int humidity = 30 + random.nextInt(71);
            int uvIndex = random.nextInt(12);

            WeatherClass weatherClass = new WeatherClass(temp, windSpeed, windDir, pressure, humidity, uvIndex);
            cities.put(city, weatherClass);
        }
        return cities;
    }
}
