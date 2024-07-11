package com.lifepill.possystem.advisor;

import com.lifepill.possystem.exception.NotFoundException;
import com.lifepill.possystem.util.StandardResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for handling application-wide exceptions.
 */
@RestControllerAdvice
public class AppWideExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StandardResponse> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(
                new StandardResponse(404, "Error", e.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    /**
     * Handles NotFoundException by returning a ResponseEntity with a corresponding error message.
     *
     * @return ResponseEntity with a StandardResponse containing the error details
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {

        // Provide a meaningful error message to the client
        String errorMessage = "An error occurred while processing your request. Please try again later.";

        // Return the error message with an internal server error status
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }
}
