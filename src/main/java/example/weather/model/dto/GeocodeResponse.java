package example.weather.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodeResponse {
    private Response response;

    @Data
    public static class Response {
        private GeoObjectCollection GeoObjectCollection;
    }

    @Data
    public static class GeoObjectCollection {
        private List<FeatureMember> featureMember;
    }

    @Data
    public static class FeatureMember {
        private GeoObject GeoObject;
    }

    @Data
    public static class GeoObject {
        private Point Point;
        private String name;
    }

    @Data
    public static class Point {
        private String pos;
    }

    public double getLat() {
        String[] coords = response.getGeoObjectCollection().getFeatureMember().get(0)
                .getGeoObject().getPoint().getPos().split(" ");
        return Double.parseDouble(coords[1]);
    }

    public double getLon() {
        String[] coords = response.getGeoObjectCollection().getFeatureMember().get(0)
                .getGeoObject().getPoint().getPos().split(" ");
        return Double.parseDouble(coords[0]);
    }
}