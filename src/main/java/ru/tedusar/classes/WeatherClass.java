package ru.tedusar.classes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WeatherClass {
    private Integer id_forecast;
    private Integer id_city;
    private final int temperature;
    private final int windSpeed;
    private final String windDirection;
    private final int pressure;
    private final int humidity;
    private final int uvIndex;
    private final LocalDateTime timestamp;

    public WeatherClass(Integer id_city, int temperature, int windSpeed,
                        String windDirection, int pressure,
                        int humidity, int uvIndex, LocalDateTime timestamp) {
        this.id_forecast = null;
        this.id_city = id_city;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.pressure = pressure;
        this.humidity = humidity;
        this.uvIndex = uvIndex;
        this.timestamp = timestamp;
    }

    public WeatherClass(int id_forecast, int id_city, int temperature, int windSpeed, String windDirection, int pressure, int humidity, int uvIndex, LocalDateTime timestamp) {
        this.id_forecast = id_forecast;
        this.id_city = id_city;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.pressure = pressure;
        this.humidity = humidity;
        this.uvIndex = uvIndex;
        this.timestamp = timestamp;
    }

    public Integer getIdForecast() { return id_forecast; }
    public Integer getCityId() { return id_city; }
    public int getTemperature() { return temperature; }
    public int getWindSpeed() { return windSpeed; }
    public String getWindDirection() { return windDirection; }
    public int getPressure() { return pressure; }
    public int getHumidity() { return humidity; }
    public int getUvIndex() { return uvIndex; }
    public LocalDateTime getTimestamp() {return timestamp;}

    public void setIdForecast(Integer id_forecast) { this.id_forecast = id_forecast; }
    public void setCityId(Integer id_city) { this.id_city = id_city; }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format(
                "температура: %d°C; скорость ветра: %d м/с; направление ветра: %s; давление: %d мм рт. ст.; влажность: %d%%; уф-индекс: %d; дата/время прогноза: %s",
                temperature, windSpeed, windDirection, pressure, humidity, uvIndex, timestamp.format(formatter)
        );
    }

}
