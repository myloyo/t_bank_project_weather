package ru.tedusar.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WeatherClass {
    private Integer idForecast;
    private Integer idCity;
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
        this.idForecast = null;
        this.idCity = id_city;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.pressure = pressure;
        this.humidity = humidity;
        this.uvIndex = uvIndex;
        this.timestamp = timestamp;
    }

    public WeatherClass(int id_forecast, int id_city, int temperature, int windSpeed, String windDirection, int pressure, int humidity, int uvIndex, LocalDateTime timestamp) {
        this.idForecast = id_forecast;
        this.idCity = id_city;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.pressure = pressure;
        this.humidity = humidity;
        this.uvIndex = uvIndex;
        this.timestamp = timestamp;
    }

    public Integer getIdForecast() { return idForecast; }
    public Integer getCityId() { return idCity; }
    public int getTemperature() { return temperature; }
    public int getWindSpeed() { return windSpeed; }
    public String getWindDirection() { return windDirection; }
    public int getPressure() { return pressure; }
    public int getHumidity() { return humidity; }
    public int getUvIndex() { return uvIndex; }
    public LocalDateTime getTimestamp() {return timestamp;}

    public void setIdForecast(Integer id_forecast) { this.idForecast = id_forecast; }
    public void setCityId(Integer id_city) { this.idCity = id_city; }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format(
                "температура: %d°C; скорость ветра: %d м/с; направление ветра: %s; давление: %d мм рт. ст.; влажность: %d%%; уф-индекс: %d; дата/время прогноза: %s",
                temperature, windSpeed, windDirection, pressure, humidity, uvIndex, timestamp.format(formatter)
        );
    }

}
