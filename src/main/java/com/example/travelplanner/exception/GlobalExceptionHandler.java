package com.example.travelplanner.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ItineraryNotFoundException.class)
    public ResponseEntity<String> handleItineraryNotFound(ItineraryNotFoundException ex) {
        logger.error("🔴 Itinerary not found: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(TripNotFoundException.class)
    public ResponseEntity<String> handleTripNotFoundException(TripNotFoundException ex) {
        logger.error("Trip not found: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleTripNotFoundException(UserNotFoundException ex) {
        logger.error("🔴 User not found: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidation(MethodArgumentNotValidException ex) {
        String errorMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .distinct()
                .collect(Collectors.joining("; "));
        logger.error("🔴 Validation failed for request: {}", errorMessages, ex);
        return ResponseEntity.badRequest().body(errorMessages);
    }

    // Handle illegal arguments
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        logger.error("🔴 Invalid argument received: {}", ex.getMessage(), ex);
        return ResponseEntity.badRequest().body("Invalid argument: " + ex.getMessage());
    }

    // Handle illegal state
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalState(IllegalStateException ex) {
        logger.error("🚨 Illegal state occurred: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Illegal state: " + ex.getMessage());
    }

    // Handle all other exceptions (fallback)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAll(Exception ex) {
        logger.error("🔴 An unexpected error occurred: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred: " + ex.getMessage());
    }
}
