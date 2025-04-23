package ru.tedusar.services;
import ru.tedusar.classes.WeatherClass;

import java.util.Random;

public class WeatherService {
    private static final String[] DIRECTIONS = {"С", "СЗ", "З", "ЮЗ", "Ю", "ЮВ", "В", "СВ"};
    private final CityService cityService;

    public WeatherService(CityService cityService) {
        this.cityService = cityService;
    }

    public static WeatherClass generateWeather() {
        Random random = new Random();
        int temp = random.nextInt(41) - 10;
        int windSpeed = random.nextInt(15);
        String windDir = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
        int pressure = 720 + random.nextInt(41);
        int humidity = 30 + random.nextInt(71);
        int uvIndex = random.nextInt(12);

        return new WeatherClass(temp, windSpeed, windDir, pressure, humidity, uvIndex);
    }

    public void initializeWeatherData() {
        for (String city : cityService.getAllCities()) {
            WeatherClass weather = WeatherService.generateWeather();
            cityService.updateWeather(city, weather);
        }
    }
}
