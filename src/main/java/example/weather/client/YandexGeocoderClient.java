package example.weather.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class YandexGeocoderClient {
        private final RestTemplate restTemplate;
        private final String baseUrl;
        private final String apiKey;

        public YandexGeocoderClient(
                        @Value("${yandex.geocoder.url}") String baseUrl,
                        @Value("${yandex.geocoder.api-key}") String apiKey) {
                this.restTemplate = new RestTemplate();
                this.baseUrl = baseUrl;
                this.apiKey = apiKey;
        }

        public String getCoordinates(String city) {
                String url = String.format("%s?geocode=%s&apikey=%s&format=json", baseUrl, city, apiKey);
                ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
                return response.getBody();
        }
}