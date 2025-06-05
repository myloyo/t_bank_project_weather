package example.weather.controller;

import example.weather.model.entity.City;
import example.weather.service.GeocodingServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/api/coords")
public class CoordsController { 
    private final GeocodingServiceImpl geocodingService;
    private final Random random = new Random();

    public CoordsController(GeocodingServiceImpl geocodingService) {
        this.geocodingService = geocodingService;
    }

    @GetMapping("/random")
    public ResponseEntity<?> getRandomCoords() {
        double lat = -90 + 180 * random.nextDouble();
        double lon = -180 + 360 * random.nextDouble();
        return ResponseEntity.ok(new double[] { lat, lon });
    }

    @GetMapping
    public ResponseEntity<?> getCoordsByCity(@RequestParam String city) {
        try {
            City c = geocodingService.geocodeCity(city);
            return ResponseEntity.ok(new double[] { c.getLatitude(), c.getLongitude() });
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Город не найден");
        }
    }
}