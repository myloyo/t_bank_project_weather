package example.weather.repository;

import example.weather.model.entity.History;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class HistoryRepository {
    private final JdbcTemplate jdbcTemplate;

    public HistoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(History history) {
        String sql = "INSERT INTO history (id_forecast, id_user, requestTime) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,
                history.getForecastId(),
                history.getUserId(),
                Timestamp.valueOf(history.getRequestTime()));
    }

    public List<History> findByUserId(Long userId) {
        String sql = "SELECT * FROM history WHERE id_user = ?";
        return jdbcTemplate.query(sql, new HistoryRowMapper(), userId);
    }

    public void deleteByUserId(Long userId) {
        String sql = "DELETE FROM history WHERE id_user = ?";
        jdbcTemplate.update(sql, userId);
    }

    private static class HistoryRowMapper implements RowMapper<History> {
        @Override
        public History mapRow(ResultSet rs, int rowNum) throws SQLException {
            History history = new History();
            history.setId(rs.getLong("id_request"));
            history.setForecastId(rs.getLong("id_forecast"));
            history.setUserId(rs.getLong("id_user"));
            history.setRequestTime(rs.getTimestamp("requestTime").toLocalDateTime());
            return history;
        }
    }
}