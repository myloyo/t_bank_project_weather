package ru.tedusar;

import java.io.*;
import java.util.*;

public class Main {
    private static final String FILE_NAME = "history.txt";

    public static void main(String[] args) {
        Map<String, Integer> cities = getCities();
        List<String> data = readHistoryFromFile(); // читаем историю из файла

        Scanner in = new Scanner(System.in);
        System.out.print("Введите город: ");
        String city = in.next();
        data.add(city); // добавляем новый город в память

        try {
            if (!cities.containsKey(city)) {
                throw new CustomException("Некорректное название города: " + city);
            } else {
                System.out.printf("В городе %s сейчас %d°C%n", city, cities.get(city));
                System.out.print("Вы также уже искали: ");
                for (String info : data) {
                    System.out.print(info + "; ");
                }
            }
        } catch (CustomException e) {
            System.out.println(e.getMessage());
        } finally {
            writeHistoryToFile(data); // сохраняем обратно в файл
            in.close();
        }
    }

    private static Map<String, Integer> getCities() {
        Map<String, Integer> cities = new HashMap<>();
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

        Random random = new Random();
        for (String city : cities.keySet()) {
            int temperature = random.nextInt(41) - 10;
            cities.put(city, temperature);
        }
        return cities;
    }

    private static List<String> readHistoryFromFile() {
        List<String> history = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    history.add(line.trim());
                }
            } catch (IOException e) {
                System.out.println("Ошибка при чтении файла истории: " + e.getMessage());
            }
        }
        return history;
    }

    private static void writeHistoryToFile(List<String> history) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String city : history) {
                writer.write(city);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл истории: " + e.getMessage());
        }
    }
}
