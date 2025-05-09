package ru.tedusar.services;

import ru.tedusar.entity.CityClass;
import ru.tedusar.entity.WeatherClass;
import ru.tedusar.repositories.CitiesRepository;
import ru.tedusar.repositories.WeatherRepository;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class CityService {
    private final CitiesRepository citiesRepository;
    private WeatherRepository weatherRepository;

    public CityService(CitiesRepository citiesRepository) {
        this.citiesRepository = citiesRepository;
    }

    public CityService(CitiesRepository citiesRepository, WeatherRepository weatherRepository) {
        this.citiesRepository = citiesRepository;
        this.weatherRepository = weatherRepository;
    }

    // Инициализация начальных городов
    public void initializeDefaultCities() throws SQLException {
        List<String> defaultCities = Arrays.asList(
                "Саратов", "Москва", "Казань", "Санкт-Петербург",
                "Екатеринбург", "Пенза", "Балаково", "Балашов", "Норильск", "Ростов"
        );

        for (String cityName : defaultCities) {
            if (!cityExists(cityName)) {
                CityClass city = new CityClass(null, cityName);
                citiesRepository.save(city);
            }
        }
    }

    // Проверка существования города
    public boolean cityExists(String cityName) throws SQLException {
        return citiesRepository.findByName(cityName) != null;
    }

    // Получение города с погодой
    public CityClass getCityWithWeather(String cityName) throws SQLException {
        CityClass city = citiesRepository.findByName(cityName);
        if (city != null) {
            WeatherClass weather = weatherRepository.findLatestByCityId(city.getId());
            city.setWeather(weather);
        }
        return city;
    }

    // Получение всех городов
    public List<CityClass> getAllCities() throws SQLException {
        return citiesRepository.findAll();
    }

    // Добавление нового города
    public void addCity(String cityName) throws SQLException {
        if (!cityExists(cityName)) {
            CityClass city = new CityClass(null, cityName);
            citiesRepository.save(city);
        }
    }

    // Обновление погоды для города
    public void updateCityWeather(String cityName, WeatherClass weather) throws SQLException {
        CityClass city = citiesRepository.findByName(cityName);
        if (city != null) {
            weather.setCityId(city.getId());
            weatherRepository.save(weather);
            city.setWeather(weather);
        }
    }

    // Удаление города
    public void deleteCity(String cityName) throws SQLException {
        CityClass city = citiesRepository.findByName(cityName);
        if (city != null) {
            citiesRepository.delete(city);
        }
    }
}