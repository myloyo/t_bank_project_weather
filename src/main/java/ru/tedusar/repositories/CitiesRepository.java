package ru.tedusar.repositories;
import ru.tedusar.entity.CityClass;
import ru.tedusar.utils.DBConnector;

import java.util.*;
import java.sql.*;

public class CitiesRepository {
    private static final String FIND_ALL_SQL = "SELECT id_city, name_city FROM city";
    private static final String FIND_BY_NAME_SQL = "SELECT id_city, name_city FROM city WHERE name_city = ?";
    private static final String SAVE_SQL = "INSERT INTO city (name_city) VALUES (?)";
    private static final String DELETE_SQL = "DELETE FROM city WHERE id_city = ?";
    private static final String UPDATE_SQL = "UPDATE city SET name_city = ? WHERE id_city = ?";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM city WHERE id_city = ?";

    public List<CityClass> findAll() throws SQLException {
        List<CityClass> cities = new ArrayList<>();

        try (Connection connection = DBConnector.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(FIND_ALL_SQL)) {

            while (rs.next()) {
                cities.add(new CityClass(
                        rs.getInt("id_city"),
                        rs.getString("name_city")
                ));
            }
        }
        return cities;
    }

    public CityClass findByName(String name) throws SQLException {
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(FIND_BY_NAME_SQL)) {

            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new CityClass(
                            rs.getInt("id_city"),
                            rs.getString("name_city")
                    );
                }
            }
        }
        return null;
    }

    public void save(CityClass city) throws SQLException {
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, city.getName());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    city.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public void delete(CityClass city) throws SQLException {
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(DELETE_SQL)) {

            stmt.setInt(1, city.getId());
            stmt.executeUpdate();
        }
    }

    public void update(CityClass city) throws SQLException {
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(UPDATE_SQL)) {

            stmt.setString(1, city.getName());
            stmt.setInt(2, city.getId());
            stmt.executeUpdate();
        }
    }

    public CityClass findById(int cityId) throws SQLException {
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(FIND_BY_ID_SQL)) {

            stmt.setInt(1, cityId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new CityClass(
                            rs.getInt("id_city"),
                            rs.getString("name_city")
                    );
                }
            }
        }
        return null;
    }
}