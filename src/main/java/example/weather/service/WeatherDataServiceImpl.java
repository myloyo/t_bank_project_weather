package example.weather.service;

import com.fasterxml.jackson.databind.JsonNode;
import example.weather.exception.WeatherServiceException;
import example.weather.model.entity.Weather;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class WeatherDataServiceImpl {

    @Value("${eol.weather.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public Weather getWeatherData(double latitude, double longitude, LocalDateTime date)
            throws WeatherServiceException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:00");
        String dateStr = date.format(formatter);
        String url = String.format(java.util.Locale.US,
                "https://projecteol.ru/api/weather?lat=%.6f&lon=%.6f&date=%s&token=%s",
                latitude, longitude, dateStr, apiKey);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, entity, JsonNode.class);
            JsonNode body = response.getBody();

            Weather weather = new Weather();
            if (body.isArray() && body.size() > 0) {
                JsonNode first = body.get(0);
                weather.setTimestamp(LocalDateTime.parse(first.get("dt_forecast").asText()));
                weather.setTemperature((int) Math.round(first.get("temp_2_cel").asDouble()));
                weather.setWindSpeed((int) Math.round(first.get("wind_speed_100").asDouble()));
                weather.setWindDirection(String.valueOf(first.get("wind_dir_100").asDouble()));
                weather.setPressure((int) Math.round(first.get("pres_surf").asDouble() / 100.0));
                weather.setHumidity((int) Math.round(first.get("vlaga_2").asDouble()));
                weather.setUvIndex(first.get("uv_index").asInt());
            } else {
                throw new WeatherServiceException("Пустой ответ от EOL API");
            }
            return weather;

        } catch (Exception e) {
            throw new WeatherServiceException("Ошибка получения погоды от EOL API", e);
        }
    }
}
