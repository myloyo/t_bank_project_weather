package example.weather.service;

import org.springframework.stereotype.Service;
import example.weather.model.dto.HistoryResponse;
import example.weather.model.dto.WeatherResponse;
import example.weather.model.entity.City;
import example.weather.model.entity.History;
import example.weather.model.entity.Weather;
import example.weather.repository.CityRepository;
import example.weather.repository.HistoryRepository;
import example.weather.repository.WeatherRepository;
import example.weather.util.DateUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final WeatherRepository weatherRepository;
    private final CityRepository cityRepository;

    public HistoryService(
            HistoryRepository historyRepository,
            WeatherRepository weatherRepository,
            CityRepository cityRepository
    ) {
        this.historyRepository = historyRepository;
        this.weatherRepository = weatherRepository;
        this.cityRepository = cityRepository;
    }

    public void saveRequest(Long forecastId, Long userId) {
        History history = new History();
        history.setForecastId(forecastId);
        history.setUserId(userId);
        history.setRequestTime(LocalDateTime.now());
        historyRepository.save(history);
    }

    public List<HistoryResponse> getUserHistory(Long userId) {
        List<History> histories = historyRepository.findByUserId(userId);
        return histories.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public void clearUserHistory(Long userId) {
        historyRepository.deleteByUserId(userId);
    }

    private HistoryResponse convertToResponse(History history) {
        Optional<Weather> weatherOpt = weatherRepository.findById(history.getForecastId());
        if (weatherOpt.isEmpty()) {
            throw new RuntimeException("Прогноз не найден");
        }
        Weather weather = weatherOpt.get();

        Optional<City> cityOpt = cityRepository.findById(weather.getCityId());
        String cityName = cityOpt.map(City::getName).orElse("Неизвестный город");

        HistoryResponse response = new HistoryResponse();
        response.setRequestTime(DateUtils.toUserFriendlyFormat(history.getRequestTime()));
        response.setCity(cityName);
        response.setWeather(convertToWeatherResponse(weather));
        return response;
    }

    private WeatherResponse convertToWeatherResponse(Weather weather) {
        WeatherResponse response = new WeatherResponse();
        response.setTemperature(weather.getTemperature());
        response.setWindSpeed(weather.getWindSpeed());
        response.setWindDirection(weather.getWindDirection());
        response.setPressure(weather.getPressure());
        response.setHumidity(weather.getHumidity());
        response.setTimestamp(weather.getTimestamp());
        return response;
    }
}
