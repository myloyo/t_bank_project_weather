package example.weather.model.dto;

import lombok.Data;

@Data
public class HistoryResponse {
    private String city;
    private String requestTime;
    private WeatherResponse weather;
}