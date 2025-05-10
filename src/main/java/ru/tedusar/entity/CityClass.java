package ru.tedusar.entity;

public class CityClass {
    private Integer id;
    private final String name;
    private WeatherClass weather;

    public CityClass(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public WeatherClass getWeather() {
        return weather;
    }

    public void setWeather(WeatherClass weather) {
        this.weather = weather;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Город: " + name + (weather != null ? "\n" + weather : "");
    }
}
