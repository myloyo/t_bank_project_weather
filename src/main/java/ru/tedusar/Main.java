package ru.tedusar;

import ru.tedusar.classes.HistoryClass;
import ru.tedusar.exceptions.*;
import ru.tedusar.services.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        HistoryService historyService = new HistoryService("history.txt");
        List<HistoryClass> history = historyService.loadHistory();

        CityService repo = new CityService();
        WeatherService weather = new WeatherService(repo);
        weather.initializeWeatherData();

        Scanner in = new Scanner(System.in);
        System.out.print("Введите город: ");
        String city = in.nextLine().trim();

        try {
            if (city.isEmpty()) {
                throw new BlankLineError("Введено пустое значение");
            }
            if (city.matches(".*[A-Za-z].*")) {
                throw new UncorrectNaming("Латинские буквы в названии: " + city);
            }
            if (city.matches(".*\\d.*") || city.matches(".*[^а-яА-Я\\s\\-].*")) {
                throw new InvalidSymbols("Название содержит недопустимые символы: " + city);
            }
            if (!repo.containsCity(city)) {
                throw new UncorrectTown("Некорректное название города: " + city);
            }

            System.out.println("Прогноз погоды для города " + city + ":");
            String weatherInfo = repo.getCity(city).toString();
            System.out.println(weatherInfo);

            history.add(new HistoryClass(city, weatherInfo));

            System.out.print("\nВы также уже искали: \n");
            for (HistoryClass info : history) {
                System.out.println("Город: " + info.getCityName());
                System.out.println(info.getWeatherInfo());
                System.out.println();
            }

            System.out.println("Очистить историю поиска? (да/нет)");
            String input = in.next();
            if (input.equalsIgnoreCase("да")) {
                history.clear();
                historyService.clear();
                System.out.println("История успешно очищена");
            }

        } catch (UncorrectTown | UncorrectNaming | BlankLineError | InvalidSymbols e) {
            System.out.println(e.getMessage());
        } finally {
            historyService.saveHistory(history);
            in.close();
        }
    }
}
