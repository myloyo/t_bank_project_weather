package example.weather.util;

import example.weather.exception.BlankLineError;
import example.weather.exception.InvalidSymbols;
import example.weather.exception.UncorrectNaming;

public class ValidationUtils {

    private static final String LATIN_LETTERS_PATTERN = ".*[A-Za-z].*";
    private static final String DIGITS_OR_INVALID_CHARS_PATTERN = ".*\\d.*|.*[^а-яА-Я\\s\\-].*";

    public static void validateCityName(String city) throws BlankLineError, UncorrectNaming, InvalidSymbols {
        if (city == null || city.trim().isEmpty()) {
            throw new BlankLineError("Введено пустое значение");
        }

        String trimmedCity = city.trim();

        if (trimmedCity.matches(LATIN_LETTERS_PATTERN)) {
            throw new UncorrectNaming("Латинские буквы в названии: " + city);
        }

        if (trimmedCity.matches(DIGITS_OR_INVALID_CHARS_PATTERN)) {
            throw new InvalidSymbols("Название содержит недопустимые символы: " + city);
        }
    }
}