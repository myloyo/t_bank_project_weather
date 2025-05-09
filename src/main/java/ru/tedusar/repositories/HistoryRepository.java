package ru.tedusar.repositories;

import ru.tedusar.entity.HistoryClass;
import ru.tedusar.utils.DBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryRepository {
    private static final String FIND_ALL_SQL = "SELECT id_request, id_forecast, id_user, requestTime FROM history";
    private static final String SAVE_SQL = "INSERT INTO history (id_forecast, id_user, requestTime) VALUES (?, ?, ?)";
    private static final String DELETE_SQL = "DELETE FROM city WHERE id_city = ?";
    private static final String FIND_BY_USER_ID_SQL = "SELECT id_request, id_forecast, id_user, requestTime FROM history WHERE id_user = ? ORDER BY requestTime DESC";
    private static final String DELETE_BY_USER_ID_SQL = "DELETE FROM history WHERE id_user = ?";
    private static final String FIND_BY_FORECAST_ID_SQL = "SELECT id_request, id_forecast, id_user, requestTime FROM history WHERE id_forecast = ?";

    public List<HistoryClass> findAll() throws SQLException {
        List<HistoryClass> history = new ArrayList<>();
        String sql = FIND_ALL_SQL;

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
        String sql = SAVE_SQL;

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, history.getIdForecast());
            stmt.setInt(2, history.getIdUser());
            stmt.setString(3, history.getRequestTime());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    history.setIdRequest(rs.getInt(1));
                }
            }
        }
    }

    public List<HistoryClass> findByUserId(int userId) throws SQLException {
        List<HistoryClass> history = new ArrayList<>();
        String sql = FIND_BY_USER_ID_SQL;

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
        String sql = DELETE_BY_USER_ID_SQL;

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }

    public List<HistoryClass> findByForecastId(int forecastId) throws SQLException {
        List<HistoryClass> history = new ArrayList<>();
        String sql = FIND_BY_FORECAST_ID_SQL;

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