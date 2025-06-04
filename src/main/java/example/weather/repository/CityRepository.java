package example.weather.repository;

import example.weather.model.entity.City;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    public void save(City city) {
        String sql = "INSERT INTO city (name_city) VALUES (?)";
        jdbcTemplate.update(sql, city.getName());
    }

    private static class CityRowMapper implements RowMapper<City> {
        @Override
        public City mapRow(ResultSet rs, int rowNum) throws SQLException {
            City city = new City();
            city.setId(rs.getLong("id_city"));
            city.setName(rs.getString("name_city"));
            return city;
        }
    }
}