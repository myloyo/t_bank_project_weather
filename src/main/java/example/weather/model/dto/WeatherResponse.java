package example.weather.model.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class WeatherResponse {
    private String city;
    private int temperature;
    private int windSpeed;
    private String windDirection;
    private int pressure;
    private int humidity;
    private LocalDateTime timestamp;
}