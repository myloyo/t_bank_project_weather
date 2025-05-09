package ru.tedusar.services;


import ru.tedusar.entity.*;
import ru.tedusar.repositories.CitiesRepository;
import ru.tedusar.repositories.WeatherRepository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class WeatherService {
    private static final String[] DIRECTIONS = {"С", "СЗ", "З", "ЮЗ", "Ю", "ЮВ", "В", "СВ"};
    private final CitiesRepository citiesRepository;
    private final WeatherRepository weatherRepository;
    private final Random random = new Random();

    public WeatherService(CitiesRepository citiesRepository, WeatherRepository weatherRepository) {
        this.citiesRepository = citiesRepository;
        this.weatherRepository = weatherRepository;
    }

    public WeatherClass generateRandomWeather(int cityId) {
        int temp = random.nextInt(41) - 10;
        int windSpeed = random.nextInt(15);
        String windDir = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
        int pressure = 720 + random.nextInt(41);
        int humidity = 30 + random.nextInt(71);
        int uvIndex = random.nextInt(12);
        LocalDateTime timestamp = LocalDateTime.now();

        return new WeatherClass(cityId, temp, windSpeed, windDir, pressure, humidity, uvIndex, timestamp);
    }

    public void updateWeatherForAllCities() throws SQLException {
        List<CityClass> cities = citiesRepository.findAll();
        for (CityClass city : cities) {
            WeatherClass weather = generateRandomWeather(city.getId());
            weatherRepository.save(weather);
            city.setWeather(weather);
        }
    }

    public void updateWeatherForCity(int cityId) throws SQLException {
        WeatherClass weather = generateRandomWeather(cityId);
        weatherRepository.save(weather);
    }

    public WeatherClass getCurrentWeather(int cityId) throws SQLException {
        return weatherRepository.findLatestByCityId(cityId);
    }

    public List<WeatherClass> getWeatherHistory(int cityId) throws SQLException {
        return weatherRepository.findForecastHistory(cityId);
    }
}