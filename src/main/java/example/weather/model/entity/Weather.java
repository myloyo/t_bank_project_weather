package example.weather.model.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Weather {
    private Long id;
    private Long cityId;
    private int temperature;
    private int windSpeed;
    private String windDirection;
    private int pressure;
    private int humidity;
    private int uvIndex;
    private LocalDateTime timestamp;
}