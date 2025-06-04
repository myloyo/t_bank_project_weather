package example.weather.service;

import example.weather.exception.BlankLineError;
import example.weather.exception.InvalidSymbols;
import example.weather.exception.UncorrectNaming;
import example.weather.exception.CityNotFoundException;
import example.weather.model.entity.City;
import example.weather.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CityService {
    private static final String LATIN_LETTERS_PATTERN = ".*[A-Za-z].*";
    private static final String DIGITS_OR_INVALID_CHARS_PATTERN = ".*\\d.*|.*[^а-яА-Я\\s\\-].*";

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public void validateCityInput(String city) throws BlankLineError, UncorrectNaming, InvalidSymbols {
        if (city == null || city.trim().isEmpty()) {
            throw new BlankLineError("Введено пустое значение");
        }
        if (city.matches(LATIN_LETTERS_PATTERN)) {
            throw new UncorrectNaming("Латинские буквы в названии: " + city);
        }
        if (city.matches(DIGITS_OR_INVALID_CHARS_PATTERN)) {
            throw new InvalidSymbols("Название содержит недопустимые символы: " + city);
        }
    }

    public City getCity(String cityName) throws BlankLineError, InvalidSymbols, UncorrectNaming, CityNotFoundException {
        validateCityInput(cityName);
        Optional<City> city = cityRepository.findByName(cityName);
        return city.orElseThrow(() -> new CityNotFoundException("Город не найден: " + cityName));
    }
}
