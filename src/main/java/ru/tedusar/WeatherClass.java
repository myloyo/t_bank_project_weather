package ru.tedusar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WeatherClass {
    private final int temperature;
    private final int windSpeed;
    private final String windDirection;
    private final int pressure;
    private final int humidity;
    private final int uvIndex;
    private final String timestamp;

    public WeatherClass(int temperature, int windSpeed, String windDirection, int pressure, int humidity, int uvIndex) {
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.pressure = pressure;
        this.humidity = humidity;
        this.uvIndex = uvIndex;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " UTC";
    }

    public String toString() {
        return String.format(
                "температура: %d°C\nскорость ветра: %d м/с\nнаправление ветра: %s\nдавление: %d мм рт. ст.\nвлажность: %d%%\nуф-индекс: %d\nдата/время прогноза: %s",
                temperature, windSpeed, windDirection, pressure, humidity, uvIndex, timestamp);
    }
}
