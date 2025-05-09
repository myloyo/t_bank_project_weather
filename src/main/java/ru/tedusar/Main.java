package ru.tedusar;

import ru.tedusar.classes.*;
import ru.tedusar.exceptions.*;
import ru.tedusar.repositories.*;
import ru.tedusar.services.*;
import ru.tedusar.utils.DBInitialize;

import java.sql.SQLException;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        try {
            DBInitialize.initialize();

            CitiesRepository citiesRepo = new CitiesRepository();
            WeatherRepository weatherRepo = new WeatherRepository();
            HistoryRepository historyRepo = new HistoryRepository();

            CityService cityService = new CityService(citiesRepo, weatherRepo);
            WeatherService weatherService = new WeatherService(citiesRepo, weatherRepo);
            HistoryService historyService = new HistoryService(historyRepo);

            cityService.initializeDefaultCities();
            weatherService.updateWeatherForAllCities();

            Scanner in = new Scanner(System.in);
            System.out.print("Введите город: ");
            String cityName = in.nextLine().trim();

            try {
                validateCityInput(cityName);
                if (!cityService.cityExists(cityName)) {
                    throw new UncorrectTown("Некорректное название города: " + cityName);
                }

                CityClass city = cityService.getCityWithWeather(cityName);
                System.out.println("Прогноз погоды для города " + cityName + ":");
                System.out.println(city.getWeather());

                WeatherClass weather = city.getWeather();
                historyService.saveRequest(weather.getIdForecast(), 1);

                System.out.println("\nИстория запросов:");
                List<HistoryClass> history = historyService.getUserHistory(1);
                for (HistoryClass entry : history) {
                    WeatherClass histWeather = weatherRepo.findById(entry.getId_forecast());
                    CityClass histCity = citiesRepo.findById(histWeather.getCityId());
                    System.out.println("Город: " + histCity.getName());
                    System.out.println(histWeather);
                    System.out.println();
                }

                System.out.println("Очистить историю поиска? (да/нет)");
                String input = in.nextLine();
                if (input.equalsIgnoreCase("да")) {
                    historyService.clearUserHistory(1);
                    System.out.println("История успешно очищена");
                }

            } catch (UncorrectTown | UncorrectNaming | BlankLineError | InvalidSymbols e) {
                System.out.println(e.getMessage());
            } finally {
                in.close();
            }

        } catch (SQLException e) {
            System.err.println("Ошибка работы с БД: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void validateCityInput(String city) throws BlankLineError, UncorrectNaming, InvalidSymbols {
        if (city.isEmpty()) {
            throw new BlankLineError("Введено пустое значение");
        }
        if (city.matches(".*[A-Za-z].*")) {
            throw new UncorrectNaming("Латинские буквы в названии: " + city);
        }
        if (city.matches(".*\\d.*") || city.matches(".*[^а-яА-Я\\s\\-].*")) {
            throw new InvalidSymbols("Название содержит недопустимые символы: " + city);
        }
    }
}