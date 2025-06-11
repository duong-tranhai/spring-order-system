package nashtech.training.ordersystem.exception;

import com.stripe.exception.StripeException;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Global exception handler to catch exceptions from all controllers.
 * Provides a consistent, structured JSON error response.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles validation errors for @Valid annotated request bodies.
     * Returns a 400 Bad Request with details about which fields are invalid.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = ex.getBindingResult().getAllErrors().stream()
                .filter(error -> Objects.nonNull(error.getDefaultMessage())) // Essential: Filter out null messages first
                .collect(Collectors.toMap(
                        error -> switch (error) {
                            // Use pattern matching to cast and get the field name in one step
                            case FieldError fieldError -> fieldError.getField();
                            // A default case handles global ObjectErrors
                            default -> error.getObjectName();
                        },
                        ObjectError::getDefaultMessage,
                        (existingValue, newValue) -> existingValue // Handle duplicate keys
                ));

        logger.warn("Validation error: {}", errors);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                "One or more fields have an error.",
                request.getDescription(false),
                errors
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles exceptions when an incorrect HTTP method is used for an endpoint.
     * Returns a 405 Method Not Allowed.
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, WebRequest request) {
        logger.warn("Method not supported: {} {}", ex.getMethod(), request.getDescription(false));
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                "Method Not Allowed",
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }


    /**
     * Handles UsernameNotFoundException, typically from Spring Security.
     * Returns a 401 Unauthorized.
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
        logger.warn("Unauthorized access attempt: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles AccessDeniedException, when a user is authenticated but not authorized.
     * Returns a 403 Forbidden.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        logger.warn("Access denied: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                "Forbidden",
                "You do not have permission to access this resource.",
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    /**
     * Handles AccessDeniedException, when a user is authenticated but not authorized.
     * Returns a 403 Forbidden.
     */
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpireException(AccessDeniedException ex, WebRequest request) {
        logger.warn("Token expired: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                "Forbidden",
                "Re-login to fetch new token.",
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    /**
     * Handles exceptions from the Stripe API.
     * Returns a 400 Bad Request or appropriate status based on the Stripe error.
     */
    @ExceptionHandler(StripeException.class)
    public ResponseEntity<ErrorResponse> handleStripeException(StripeException ex, WebRequest request) {
        logger.error("Stripe API error: {}", ex.getMessage());
        HttpStatus status = (ex.getStatusCode() != null)
                ? HttpStatus.valueOf(ex.getStatusCode())
                : HttpStatus.BAD_REQUEST;

        ErrorResponse errorResponse = new ErrorResponse(
                status.value(),
                "Payment Error",
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, status);
    }

    /**
     * A catch-all handler for any other unhandled exceptions.
     * Returns a 500 Internal Server Error to avoid leaking implementation details.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        // Log the full stack trace for debugging
        logger.error("An unexpected error occurred: ", ex);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "An unexpected error occurred. Please try again later.",
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}