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

@Service
public class WeatherDataServiceImpl {

    @Value("${eol.weather.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public Weather getWeatherData(double latitude, double longitude) throws WeatherServiceException {
        String url = String.format("https://projecteol.ru/api/weather?lat=%.6f&lon=%.6f", latitude, longitude);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, entity, JsonNode.class);
            JsonNode body = response.getBody();

            Weather weather = new Weather();
            weather.setTemperature(body.get("temperature").asInt());
            weather.setWindSpeed(body.get("wind_speed").asInt());
            weather.setWindDirection(body.get("wind_direction").asText());
            weather.setPressure(body.get("pressure").asInt());
            weather.setHumidity(body.get("humidity").asInt());
            weather.setUvIndex(body.get("uv_index").asInt());
            weather.setTimestamp(LocalDateTime.now());
            return weather;

        } catch (Exception e) {
            throw new WeatherServiceException("Ошибка получения погоды от EOL API", e);
        }
    }
}
