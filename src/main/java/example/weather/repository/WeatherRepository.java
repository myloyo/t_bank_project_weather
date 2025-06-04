package example.weather.repository;

import example.weather.model.entity.Weather;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class WeatherRepository {
    private final JdbcTemplate jdbcTemplate;

    public WeatherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Weather weather) {
        String sql = "INSERT INTO forecast (id_city, temp, wind_speed, wind_direction, pressure, humidity, uvIndex, timestamp) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                weather.getCityId(),
                weather.getTemperature(),
                weather.getWindSpeed(),
                weather.getWindDirection(),
                weather.getPressure(),
                weather.getHumidity(),
                weather.getUvIndex(),
                Timestamp.valueOf(weather.getTimestamp()));
    }

    public Optional<Weather> findById(Long id) {
        String sql = "SELECT * FROM forecast WHERE id_forecast = ?";
        try {
            Weather weather = jdbcTemplate.queryForObject(sql, new WeatherRowMapper(), id);
            return Optional.ofNullable(weather);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private static class WeatherRowMapper implements RowMapper<Weather> {
        @Override
        public Weather mapRow(ResultSet rs, int rowNum) throws SQLException {
            Weather weather = new Weather();
            weather.setId(rs.getLong("id_forecast"));
            weather.setCityId(rs.getLong("id_city"));
            weather.setTemperature(rs.getInt("temp"));
            weather.setWindSpeed(rs.getInt("wind_speed"));
            weather.setWindDirection(rs.getString("wind_direction"));
            weather.setPressure(rs.getInt("pressure"));
            weather.setHumidity(rs.getInt("humidity"));
            weather.setUvIndex(rs.getInt("uvIndex"));
            weather.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
            return weather;
        }
    }
}