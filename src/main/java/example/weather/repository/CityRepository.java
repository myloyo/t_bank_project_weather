package example.weather.repository;

import example.weather.model.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByName(String name);

    Optional<City> findByLatitudeAndLongitude(Double latitude, Double longitude);

    @Query("SELECT c FROM City c WHERE ABS(c.latitude - :lat) < 0.0001 AND ABS(c.longitude - :lon) < 0.0001")
    Optional<City> findByCoordsApprox(@Param("lat") Double lat, @Param("lon") Double lon);
}