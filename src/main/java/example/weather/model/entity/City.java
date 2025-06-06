package example.weather.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_city")
    private Long id;
    @Column(name = "name_city")
    private String name;
    private Double latitude;
    private Double longitude;
}