package example.weather.model.entity;

import lombok.Data;

@Data
public class City {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
}