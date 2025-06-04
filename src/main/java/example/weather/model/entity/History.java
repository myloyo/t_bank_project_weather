package example.weather.model.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class History {
    private Long id;
    private Long forecastId;
    private Long userId;
    private LocalDateTime requestTime;
}