package example.weather.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import example.weather.model.dto.ApiError;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            BlankLineError.class,
            InvalidSymbols.class,
            UncorrectNaming.class
    })
    public ResponseEntity<ApiError> handleValidationExceptions(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(ex.getMessage(), "VALIDATION_ERROR"));
    }

    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<ApiError> handleCityNotFound(CityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(ex.getMessage(), "CITY_NOT_FOUND"));
    }

    @ExceptionHandler(WeatherServiceException.class)
    public ResponseEntity<ApiError> handleWeatherServiceError(WeatherServiceException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new ApiError(ex.getMessage(), "WEATHER_SERVICE_ERROR"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        if (path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-resources")) {
            throw ex;
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError("Internal server error", "INTERNAL_ERROR"));
    }
}