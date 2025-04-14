package ru.tedusar;

import java.util.*;

import static ru.tedusar.CitiesRepository.*;
import static ru.tedusar.HistoryService.*;

public class Main {
    public static void main(String[] args) {
        List<String> data = readHistoryFromFile();
        setCities();

        Scanner in = new Scanner(System.in);
        System.out.print("Введите город: ");
        String city = in.next();
        data.add(city); // добавляем новый город в память

        try {
            if (city.matches(".*[A-Za-z].*")) {
                throw new UncorrectNaming("Латинские буквы в названии: " + city);
            }
            if (!cities.containsKey(city)) {
                throw new UncorrectTown("Некорректное название города: " + city);
            } else {
                System.out.printf("В городе %s сейчас %d°C%n", city, cities.get(city));
                System.out.print("Вы также уже искали: ");
                for (String info : data) {
                    System.out.print(info + "; ");
                }
                System.out.println();
                System.out.println("очистить историю поиска? (да/нет)");
                String input = in.next();
                if (input.equals("да")) {
                    deleteHistory(data);
                    System.out.println("история успешно очищена");
                }
            }
        } catch (UncorrectTown | UncorrectNaming e) {
            System.out.println(e.getMessage());
        } finally {
            writeHistoryToFile(data); // сохраняем обратно в файл
            in.close();
        }
    }
}
