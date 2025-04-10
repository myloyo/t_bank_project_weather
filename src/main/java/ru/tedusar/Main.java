package ru.tedusar;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<String, Integer> cities = getCities();

        Scanner in = new Scanner(System.in); // ввод с клавы города
        System.out.print("Введите город: ");
        String city = in.next();

        try {
            if (!cities.containsKey(city)) {
                throw new CustomException("Некорректное название города: " + city);
            }
            else {
                System.out.printf("В городе " + city + " сейчас " + cities.get(city) + "°C");
            }
        } catch (CustomException e) {
            System.out.println(e.getMessage());
        } finally {
            in.close();
        }
    }

    private static Map<String, Integer> getCities() {
        Map<String, Integer> cities = new HashMap<>(); // сама хэшмэпа
        cities.put("Саратов", 0);
        cities.put("Москва", 0);
        cities.put("Казань", 0);
        cities.put("Санкт-Петербург", 0);
        cities.put("Екатеринбург", 0);
        cities.put("Пенза", 0);
        cities.put("Балаково", 0);
        cities.put("Балашов", 0);
        cities.put("Норильск", 0);

        Random random = new Random(); // заполняю по ключам значения
        for (String city : cities.keySet()) {
            int temperature = random.nextInt(41) - 10;
            cities.put(city, temperature);
        }
        return cities;
    }
}
