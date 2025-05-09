package ru.tedusar.repositories;

import ru.tedusar.classes.HistoryClass;
import ru.tedusar.utils.DBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryRepository {

    public List<HistoryClass> findAll() throws SQLException {
        List<HistoryClass> history = new ArrayList<>();
        String sql = "SELECT id_request, id_forecast, id_user, requestTime FROM history";

        try (Connection connection = DBConnector.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                history.add(new HistoryClass(
                        rs.getInt("id_request"),
                        rs.getInt("id_forecast"),
                        rs.getInt("id_user"),
                        rs.getString("requestTime")
                ));
            }
        }
        return history;
    }

    public void save(HistoryClass history) throws SQLException {
        String sql = "INSERT INTO history (id_forecast, id_user, requestTime) VALUES (?, ?, ?)";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, history.getId_forecast());
            stmt.setInt(2, history.getId_user());
            stmt.setString(3, history.getRequestTime());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    history.setId_request(rs.getInt(1));
                }
            }
        }
    }

    public List<HistoryClass> findByUserId(int userId) throws SQLException {
        List<HistoryClass> history = new ArrayList<>();
        String sql = "SELECT id_request, id_forecast, id_user, requestTime FROM history WHERE id_user = ? ORDER BY requestTime DESC";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    history.add(new HistoryClass(
                            rs.getInt("id_request"),
                            rs.getInt("id_forecast"),
                            rs.getInt("id_user"),
                            rs.getString("requestTime")
                    ));
                }
            }
        }
        return history;
    }

    public void deleteByUserId(int userId) throws SQLException {
        String sql = "DELETE FROM history WHERE id_user = ?";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }

    public List<HistoryClass> findByForecastId(int forecastId) throws SQLException {
        List<HistoryClass> history = new ArrayList<>();
        String sql = "SELECT id_request, id_forecast, id_user, requestTime FROM history WHERE id_forecast = ?";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, forecastId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    history.add(new HistoryClass(
                            rs.getInt("id_request"),
                            rs.getInt("id_forecast"),
                            rs.getInt("id_user"),
                            rs.getString("requestTime")
                    ));
                }
            }
        }
        return history;
    }
}