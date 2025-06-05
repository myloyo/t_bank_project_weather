package example.weather.model.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ApiError {
    private String message;
    private String errorCode;
    private LocalDateTime timestamp;

    public ApiError(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now();
    }
}