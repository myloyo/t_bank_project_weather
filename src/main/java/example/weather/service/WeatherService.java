package example.weather.service;

import example.weather.exception.*;
import org.springframework.stereotype.Service;
import example.weather.model.dto.WeatherResponse;
import example.weather.model.entity.City;
import example.weather.model.entity.Weather;
import example.weather.repository.WeatherRepository;
import example.weather.service.GeocodingServiceImpl;
import example.weather.service.WeatherDataServiceImpl;

@Service
public class WeatherService {
    private final GeocodingServiceImpl geocodingService;
    private final WeatherDataServiceImpl weatherDataService;
    private final CityService cityService;
    private final WeatherRepository weatherRepository;
    private final HistoryService historyService;

    public WeatherService(
            GeocodingServiceImpl geocodingService,
            WeatherDataServiceImpl weatherDataService,
            CityService cityService,
            WeatherRepository weatherRepository,
            HistoryService historyService) {
        this.geocodingService = geocodingService;
        this.weatherDataService = weatherDataService;
        this.cityService = cityService;
        this.weatherRepository = weatherRepository;
        this.historyService = historyService;
    }

    public WeatherResponse getWeatherForCity(String cityName, Long userId)
            throws BlankLineError, InvalidSymbols, UncorrectNaming, CityNotFoundException, WeatherServiceException {
        City city = cityService.getCity(cityName);

        Weather weather = weatherDataService.getWeatherData(
                city.getLatitude(),
                city.getLongitude());

        weather.setCityId(city.getId());
        weatherRepository.save(weather);

        if (userId != null) {
            historyService.saveRequest(weather.getId(), userId);
        }

        return mapToResponse(city, weather);
    }

    public WeatherResponse getWeatherByCoords(double lat, double lon, Long userId) {
        try {
            Weather weather = weatherDataService.getWeatherData(lat, lon);
            WeatherResponse response = new WeatherResponse();
            response.setCity(null);
            response.setTemperature(weather.getTemperature());
            response.setWindSpeed(weather.getWindSpeed());
            response.setWindDirection(weather.getWindDirection());
            response.setPressure(weather.getPressure());
            response.setHumidity(weather.getHumidity());
            response.setTimestamp(weather.getTimestamp());
            if (userId != null) {
                historyService.saveRequest(weather.getId(), userId);
            }
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка получения погоды по координатам: " + e.getMessage());
        }
    }

    private WeatherResponse mapToResponse(City city, Weather weather) {
        WeatherResponse response = new WeatherResponse();
        response.setCity(city.getName());
        response.setTemperature(weather.getTemperature());
        response.setWindSpeed(weather.getWindSpeed());
        response.setWindDirection(weather.getWindDirection());
        response.setPressure(weather.getPressure());
        response.setHumidity(weather.getHumidity());
        response.setTimestamp(weather.getTimestamp());
        return response;
    }
}
