package ru.tedusar.repositories;
import ru.tedusar.classes.CityClass;
import ru.tedusar.utils.DBConnector;

import java.util.*;
import java.sql.*;

public class CitiesRepository {

    public List<CityClass> findAll() throws SQLException {
        List<CityClass> cities = new ArrayList<>();
        String sql = "SELECT id_city, name_city FROM city";

        try (Connection connection = DBConnector.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

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
        String sql = "SELECT id_city, name_city FROM city WHERE name_city = ?";

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

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
        String sql = "INSERT INTO city (name_city) VALUES (?)";

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

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
        String sql = "DELETE FROM city WHERE id_city = ?";
        try (Connection connection = DBConnector.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, city.getId());
            stmt.executeUpdate();

            try(ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    city.setId(generatedKeys.getInt(1));
                }
            }


        }
    }

    public void update(CityClass city) throws SQLException {
        String sql = "UPDATE city SET name_city = ? WHERE id_city = ?";
        try (Connection connection = DBConnector.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, city.getName());
            stmt.setInt(2, city.getId());
            stmt.executeUpdate();

            try(ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    city.setId(generatedKeys.getInt(1));
                }
            }

        }
    }

    public CityClass findById(int cityId) throws SQLException {
        String sql = "SELECT * FROM city WHERE id_city = ?";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

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
