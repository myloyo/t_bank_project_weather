package ru.tedusar.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitialize {
    public static void initialize() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS city (" +
                "id_city SERIAL PRIMARY KEY," +
                "name_city VARCHAR(100) NOT NULL UNIQUE" +
                ");" +

                "CREATE TABLE IF NOT EXISTS forecast (" +
                "id_forecast SERIAL PRIMARY KEY," +
                "id_city INTEGER REFERENCES city(id_city)," +
                "temp INTEGER NOT NULL," +
                "wind_speed INTEGER NOT NULL," +
                "wind_direction VARCHAR(10) NOT NULL," +
                "pressure INTEGER NOT NULL," +
                "humidity INTEGER NOT NULL," +
                "uvIndex INTEGER NOT NULL," +
                "timestamp TIMESTAMP NOT NULL" +
                ");" +

                "CREATE TABLE IF NOT EXISTS history (" +
                "id_request SERIAL PRIMARY KEY," +
                "id_forecast INTEGER REFERENCES forecast(id_forecast)," +
                "id_user INTEGER NOT NULL," +
                "requestTime VARCHAR(50) NOT NULL" +
                ");";

        try (Connection conn = DBConnector.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            if (e.getMessage().contains("database \"weather_db\" does not exist")) {
                createDatabase();
                initialize();
            } else {
                throw e;
            }
        }
    }

    private static void createDatabase() throws SQLException {
        try (Connection conn = DBConnector.getRootConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE DATABASE weather_db");
        }
    }
}