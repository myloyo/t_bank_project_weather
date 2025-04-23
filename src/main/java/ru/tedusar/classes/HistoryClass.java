package ru.tedusar.classes;

public class HistoryClass {
    private final String cityName;
    private final String weatherInfo;

    public HistoryClass(String cityName, String weatherInfo) {
        this.cityName = cityName;
        this.weatherInfo = weatherInfo;
    }

    public String getCityName() {
        return cityName;
    }

    public String getWeatherInfo() {
        return weatherInfo;
    }

    @Override
    public String toString() {
        return cityName + ": " + weatherInfo;
    }

    public static HistoryClass fromString(String line) {
        String[] parts = line.split(": ", 2);
        if (parts.length < 2) return null;
        return new HistoryClass(parts[0].trim(), parts[1].trim());
    }
}
