package ru.tedusar;

import ru.tedusar.Exceptions.BlankLineError;
import ru.tedusar.Exceptions.UncorrectNaming;
import ru.tedusar.Exceptions.UncorrectTown;
import ru.tedusar.Exceptions.InvalidSymbols;

import java.util.*;
import static ru.tedusar.CitiesRepository.*;
import static ru.tedusar.Services.HistoryService.*;

public class Main {
    public static void main(String[] args) {
        List<String> data = readHistoryFromFile();
        setCities();

        Scanner in = new Scanner(System.in);
        System.out.print("Введите город: ");
        String city = in.nextLine().trim();
        data.add(city); // добавляем новый город в память

        try {
            if (city.matches(".*[A-Za-z].*")) {
                throw new UncorrectNaming("Латинские буквы в названии: " + city);
            }
            if (city.isEmpty()){
                throw new BlankLineError("Введено пустое значение");
            }
            if (city.matches(".*\\d.*") || city.matches(".*[^а-яА-Я\\s\\-].*")) {
                throw new InvalidSymbols("Название содержит недопустимые символы: " + city);
            }
            if (!cities.containsKey(city)) {
                throw new UncorrectTown("Некорректное название города: " + city);
            } else {
                System.out.println("Прогноз погоды для города " + city + ":");
                System.out.println(cities.get(city));
                System.out.print("\nВы также уже искали: ");
                for (String info : data) {
                    System.out.print(info + "; ");
                }
                System.out.println("\n");
                System.out.println("очистить историю поиска? (да/нет)");
                String input = in.next();
                if (input.equals("да")) {
                    deleteHistory(data);
                    System.out.println("история успешно очищена");
                }
            }
        } catch (UncorrectTown | UncorrectNaming | BlankLineError | InvalidSymbols e) {
            System.out.println(e.getMessage());
        } finally {
            writeHistoryToFile(data); // сохраняем обратно в файл
            in.close();
        }
    }
}
