package nashtech.training.ordersystem.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * A custom error response class to provide a structured, consistent error format.
 */
@Setter
@Getter
public class ErrorResponse {
    // Getters and Setters
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private Map<String, String> details; // For validation errors

    // Constructor for general errors
    public ErrorResponse(int status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    // Constructor for validation errors
    public ErrorResponse(int status, String error, String message, String path, Map<String, String> details) {
        this(status, error, message, path);
        this.details = details;
    }

}