package example.weather.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import example.weather.model.dto.WeatherResponse;
import example.weather.service.WeatherService;
import org.springframework.security.core.Authentication;
import example.weather.service.UserService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    private final WeatherService weatherService;
    private final UserService userService;

    public WeatherController(WeatherService weatherService, UserService userService) {
        this.weatherService = weatherService;
        this.userService = userService;
    }

    @GetMapping("/{city}")
    public ResponseEntity<WeatherResponse> getWeather(
            @PathVariable String city, Authentication authentication) {
        try {
            Long userId = null;
            if (authentication != null) {
                String username = authentication.getName();
                userId = userService.findByUsername(username).map(u -> u.getId()).orElse(null);
            }
            WeatherResponse response = weatherService.getWeatherForCity(city, userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/by-coords")
    public ResponseEntity<WeatherResponse> getWeatherByCoords(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam("dateTime") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime dateTime,
            Authentication authentication) {
        Long userId = null;
        if (authentication != null) {
            String username = authentication.getName();
            userId = userService.findByUsername(username).map(u -> u.getId()).orElse(null);
        }
        WeatherResponse response = weatherService.getWeatherByCoords(lat, lon, dateTime, userId);
        return ResponseEntity.ok(response);
    }
}