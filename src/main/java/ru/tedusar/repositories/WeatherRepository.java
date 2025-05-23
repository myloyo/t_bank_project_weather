package ru.tedusar.repositories;

import ru.tedusar.entity.WeatherClass;
import ru.tedusar.utils.DBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WeatherRepository {
    private static final String SAVE_SQL = "INSERT INTO forecast (id_city, temp, wind_speed, wind_direction, pressure, humidity, uvIndex, timestamp) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_LATEST_BY_CITY_ID_SQL = "SELECT * FROM forecast WHERE id_city = ? ORDER BY timestamp DESC LIMIT 1";
    private static final String FIND_FORECAST_HISTORY_SQL = "SELECT * FROM forecast WHERE id_city = ? ORDER BY timestamp DESC";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM forecast WHERE id_forecast = ?";

    public void save(WeatherClass weather) throws SQLException {
        String sql = SAVE_SQL;

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, weather.getCityId());
            stmt.setInt(2, weather.getTemperature());
            stmt.setInt(3, weather.getWindSpeed());
            stmt.setString(4, weather.getWindDirection());
            stmt.setInt(5, weather.getPressure());
            stmt.setInt(6, weather.getHumidity());
            stmt.setInt(7, weather.getUvIndex());
            stmt.setTimestamp(8, Timestamp.valueOf(weather.getTimestamp()));

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    weather.setIdForecast(generatedKeys.getInt(1));
                }
            }
        }
    }

    public WeatherClass findLatestByCityId(int cityId) throws SQLException {
        String sql = FIND_LATEST_BY_CITY_ID_SQL;

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cityId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {

                    String timestamp = rs.getString("timestamp");
                    return new WeatherClass(
                            rs.getInt("id_forecast"),
                            rs.getInt("id_city"),
                            rs.getInt("temp"),
                            rs.getInt("wind_speed"),
                            rs.getString("wind_direction"),
                            rs.getInt("pressure"),
                            rs.getInt("humidity"),
                            rs.getInt("uvIndex"),
                            rs.getTimestamp("timestamp").toLocalDateTime()
                    );
                }
            }
        }
        return null;
    }

    public List<WeatherClass> findForecastHistory(int cityId) throws SQLException {
        List<WeatherClass> forecasts = new ArrayList<>();
        String sql = FIND_FORECAST_HISTORY_SQL;

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cityId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String timestamp = rs.getString("timestamp");
                    forecasts.add(new WeatherClass(
                            rs.getInt("id_forecast"),
                            rs.getInt("id_city"),
                            rs.getInt("temp"),
                            rs.getInt("wind_speed"),
                            rs.getString("wind_direction"),
                            rs.getInt("pressure"),
                            rs.getInt("humidity"),
                            rs.getInt("uvIndex"),
                            rs.getTimestamp("timestamp").toLocalDateTime()
                    ));
                }
            }
        }
        return forecasts;
    }

    public WeatherClass findById(int forecastId) throws SQLException {
        String sql = FIND_BY_ID_SQL;

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, forecastId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new WeatherClass(
                            rs.getInt("id_forecast"),
                            rs.getInt("id_city"),
                            rs.getInt("temp"),
                            rs.getInt("wind_speed"),
                            rs.getString("wind_direction"),
                            rs.getInt("pressure"),
                            rs.getInt("humidity"),
                            rs.getInt("uvIndex"),
                            rs.getTimestamp("timestamp").toLocalDateTime()
                    );
                }
            }
        }
        return null;
    }
}