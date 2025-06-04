package example.weather.model.dto;

import lombok.Data;

@Data
public class HistoryResponse {
    private String requestTime;
    private WeatherResponse weather;
}