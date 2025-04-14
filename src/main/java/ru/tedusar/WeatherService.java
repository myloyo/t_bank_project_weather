package ru.tedusar;

import java.util.Map;
import java.util.Random;

import static ru.tedusar.CitiesRepository.*;

public class WeatherService {
    public static Map<String, Integer> setWeather(){
        Random random = new Random();
        for (String city : cities.keySet()) {
            int temperature = random.nextInt(41) - 10;
            cities.put(city, temperature);
        }
        return cities;
    }
}
