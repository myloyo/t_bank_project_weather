package ru.tedusar.services;

import ru.tedusar.classes.HistoryClass;
import ru.tedusar.repositories.HistoryRepository;

import java.util.List;

public class HistoryService {
    private final HistoryRepository repository;

    public HistoryService(HistoryRepository repository) {
        this.repository = repository;
    }

    public HistoryService(String fileName) {
        this.repository = new HistoryRepository(fileName);
    }

    public List<HistoryClass> loadHistory() {
        return repository.readHistory();
    }

    public void saveHistory(List<HistoryClass> history) {
        repository.writeHistory(history);
    }

    public void clear() {
        repository.clearHistory();
    }
}
