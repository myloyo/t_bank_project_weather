package ru.tedusar.services;

import ru.tedusar.entity.HistoryClass;
import ru.tedusar.repositories.HistoryRepository;

import java.sql.SQLException;
import java.util.List;

public class HistoryService {
    private final HistoryRepository repository;

    public HistoryService(HistoryRepository repository) {
        this.repository = repository;
    }

    public void saveRequest(int forecastId, int userId) {
        try {
            HistoryClass historyEntry = new HistoryClass(forecastId, userId);
            repository.save(historyEntry);
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось сохранить историю запроса", e);
        }
    }

    public List<HistoryClass> getUserHistory(int userId) {
        try {
            return repository.findByUserId(userId);
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось загрузить историю запросов", e);
        }
    }

    public void clearUserHistory(int userId) {
        try {
            repository.deleteByUserId(userId);
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось очистить историю запросов", e);
        }
    }

    public HistoryClass getLastUserRequest(int userId) {
        List<HistoryClass> history = getUserHistory(userId);
        return history.isEmpty() ? null : history.get(0);
    }
}