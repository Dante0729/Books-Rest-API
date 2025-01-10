package com.portfolio.davidreyes.booksapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for the application.
 * Provides centralized exception handling across all controllers.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles `IllegalArgumentException`.
     *
     * @param ex The exception thrown.
     * @return A `ResponseEntity` with a `400 Bad Request` status and a descriptive error message.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body("Illegal Argument: " + ex.getMessage());
    }

    /**
     * Handles `IllegalStateException`.
     *
     * @param ex The exception thrown.
     * @return A `ResponseEntity` with a `404 Not Found` status and a descriptive error message.
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + ex.getMessage());
    }


}
