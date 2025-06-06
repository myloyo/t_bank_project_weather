package example.weather.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "history")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_request")
    private Long id;
    @Column(name = "id_forecast")
    private Long forecastId;
    @Column(name = "id_user")
    private Long userId;
    @Column(name = "requestTime")
    private LocalDateTime requestTime;
}