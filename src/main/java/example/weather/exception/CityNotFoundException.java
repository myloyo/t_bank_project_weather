package example.weather.exception;

public class CityNotFoundException extends Exception {
    public CityNotFoundException(String city) {
        super("Город не найден: " + city);
    }
}