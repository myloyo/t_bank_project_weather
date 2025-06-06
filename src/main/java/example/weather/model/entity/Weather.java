package example.weather.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "forecast")
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_forecast")
    private Long id;
    @Column(name = "id_city")
    private Long cityId;
    @Column(name = "temp")
    private int temperature;
    @Column(name = "wind_speed")
    private int windSpeed;
    @Column(name = "wind_direction")
    private String windDirection;
    @Column(name = "pressure")
    private int pressure;
    @Column(name = "humidity")
    private int humidity;
    @Column(name = "uvIndex")
    private int uvIndex;
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}