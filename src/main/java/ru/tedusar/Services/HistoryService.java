package ru.tedusar.Services;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryService {
    private static final String FILE_NAME = "history.txt";

    public static List<String> readHistoryFromFile() {
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

    public static void writeHistoryToFile(List<String> history) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String city : history) {
                writer.write(city);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл истории: " + e.getMessage());
        }
    }

    public static void deleteHistory(List<String> history){
        history.clear();
    }
}
