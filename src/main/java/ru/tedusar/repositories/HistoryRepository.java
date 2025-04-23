package ru.tedusar.repositories;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import ru.tedusar.classes.HistoryClass;

public class HistoryRepository {
    private final String fileName;

    public HistoryRepository(String fileName) {
        this.fileName = fileName;
    }

    public List<HistoryClass> readHistory() {
        List<HistoryClass> history = new ArrayList<>();
        File file = new File(fileName);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|", 2);
                    if (parts.length == 2) {
                        history.add(new HistoryClass(parts[0], parts[1]));
                    }
                }
            } catch (IOException e) {
                System.out.println("Ошибка при чтении истории: " + e.getMessage());
            }
        }
        return history;
    }

    public void writeHistory(List<HistoryClass> history) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (HistoryClass entry : history) {
                writer.write(entry.getCityName() + "|" + entry.getWeatherInfo());
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Ошибка при записи истории: " + e.getMessage());
        }
    }

    public void clearHistory() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

        } catch (IOException e) {
            System.out.println("Ошибка при очистке истории: " + e.getMessage());
        }
    }
}

