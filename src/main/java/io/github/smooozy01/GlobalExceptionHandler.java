package io.github.smooozy01;

import io.github.smooozy01.exception.general.*;
import io.github.smooozy01.exception.refuel.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Loop through each failure (price, mileage, etc.)
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
        
    /// 409
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleAlreadyExists(RuntimeException ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }
    
    /// 404
    @ExceptionHandler(DoesntExistException.class)
    public ResponseEntity<String> handleDoesntExist(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    ///400
    @ExceptionHandler({
            InvalidDateException.class,
            InvalidFuelException.class,
            InvalidPriceException.class,
            InvalidMileageException.class
    })
    public ResponseEntity<Map<String, String>> handleCustomBadRequests(RuntimeException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    private ResponseEntity<Map<String, String>> buildResponse(HttpStatus status, String message) {
        Map<String, String> body = new HashMap<>();
        body.put("error", message); // Frontend always checks 'error' key
        return ResponseEntity.status(status).body(body);
    }
    
}
