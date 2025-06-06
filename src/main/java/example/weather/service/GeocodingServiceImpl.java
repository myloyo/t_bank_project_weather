package example.weather.service;

import example.weather.exception.CityNotFoundException;
import example.weather.model.entity.City;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeocodingServiceImpl {
    private final String apiKey;
    private final String apiUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    public GeocodingServiceImpl(
            @Value("${yandex.geocoder.api-key}") String apiKey,
            @Value("${yandex.geocoder.url}") String apiUrl) {
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
    }

    public City geocodeCity(String cityName) throws CityNotFoundException {
        String requestUrl = String.format("%s?apikey=%s&geocode=%s&lang=ru_RU&results=1&format=json", apiUrl, apiKey,
                cityName);
        ResponseEntity<String> response = restTemplate.getForEntity(requestUrl, String.class);

        try {
            JSONObject json = new JSONObject(response.getBody());
            JSONArray featureMembers = json.getJSONObject("response")
                    .getJSONObject("GeoObjectCollection")
                    .getJSONArray("featureMember");
            if (featureMembers.length() == 0)
                throw new CityNotFoundException(cityName);
            JSONObject geoObject = featureMembers.getJSONObject(0).getJSONObject("GeoObject");
            String pos = geoObject.getJSONObject("Point").getString("pos");
            String[] coords = pos.split(" ");
            double lon = Double.parseDouble(coords[0]);
            double lat = Double.parseDouble(coords[1]);
            City city = new City();
            city.setName(cityName);
            city.setLatitude(lat);
            city.setLongitude(lon);
            return city;
        } catch (Exception e) {
            throw new CityNotFoundException("Ошибка при получении координат города: " + cityName);
        }
    }

    public String reverseGeocode(double lat, double lon) {
        String requestUrl = String.format("%s?apikey=%s&geocode=%f,%f&lang=ru_RU&results=1&format=json", apiUrl, apiKey,
                lon, lat);
        ResponseEntity<String> response = restTemplate.getForEntity(requestUrl, String.class);
        try {
            JSONObject json = new JSONObject(response.getBody());
            JSONArray featureMembers = json.getJSONObject("response")
                    .getJSONObject("GeoObjectCollection")
                    .getJSONArray("featureMember");
            if (featureMembers.length() == 0)
                return "";
            JSONObject geoObject = featureMembers.getJSONObject(0).getJSONObject("GeoObject");
            return geoObject.getString("name");
        } catch (Exception e) {
            return "";
        }
    }
}
