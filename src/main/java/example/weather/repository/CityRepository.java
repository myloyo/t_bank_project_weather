package example.weather.repository;

import example.weather.model.entity.City;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Optional;

@Repository
public class CityRepository {
    private final JdbcTemplate jdbcTemplate;

    public CityRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<City> findById(Long id) {
        String sql = "SELECT * FROM city WHERE id_city = ?";
        try {
            City city = jdbcTemplate.queryForObject(sql, new CityRowMapper(), id);
            return Optional.ofNullable(city);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<City> findByName(String name) {
        String sql = "SELECT * FROM city WHERE name_city = ?";
        try {
            City city = jdbcTemplate.queryForObject(sql, new CityRowMapper(), name);
            return Optional.ofNullable(city);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public City save(City city) {
        String sql = "INSERT INTO city (name_city, latitude, longitude) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, city.getName());
            ps.setDouble(2, city.getLatitude());
            ps.setDouble(3, city.getLongitude());
            return ps;
        }, keyHolder);
        Map<String, Object> keys = keyHolder.getKeys();
        if (keys != null && keys.get("id_city") != null) {
            city.setId(keys.get("id_city") instanceof Number ? ((Number) keys.get("id_city")).longValue() : null);
        }
        return city;
    }

    private static class CityRowMapper implements RowMapper<City> {
        @Override
        public City mapRow(ResultSet rs, int rowNum) throws SQLException {
            City city = new City();
            city.setId(rs.getLong("id_city"));
            city.setName(rs.getString("name_city"));
            city.setLatitude(rs.getDouble("latitude"));
            city.setLongitude(rs.getDouble("longitude"));
            return city;
        }
    }
}