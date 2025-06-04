package example.weather.repository;

import example.weather.model.entity.Weather;
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
public class WeatherRepository {
    private final JdbcTemplate jdbcTemplate;

    public WeatherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Weather save(Weather weather) {
        String sql = "INSERT INTO forecast (id_city, temp, wind_speed, wind_direction, pressure, humidity, uvIndex, timestamp) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, weather.getCityId(), java.sql.Types.BIGINT);
            ps.setObject(2, weather.getTemperature(), java.sql.Types.INTEGER);
            ps.setObject(3, weather.getWindSpeed(), java.sql.Types.INTEGER);
            ps.setString(4, weather.getWindDirection());
            ps.setObject(5, weather.getPressure(), java.sql.Types.INTEGER);
            ps.setObject(6, weather.getHumidity(), java.sql.Types.INTEGER);
            ps.setObject(7, weather.getUvIndex(), java.sql.Types.INTEGER);
            ps.setObject(8, weather.getTimestamp() != null ? java.sql.Timestamp.valueOf(weather.getTimestamp()) : null,
                    java.sql.Types.TIMESTAMP);
            return ps;
        }, keyHolder);

        Map<String, Object> keys = keyHolder.getKeys();
        if (keys != null && keys.get("id_forecast") != null) {
            weather.setId(((Number) keys.get("id_forecast")).longValue());
        }
        return weather;
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