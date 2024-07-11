package com.lifePill.SupplierService.advisor;

import com.lifePill.SupplierService.exception.NotFoundException;
import com.lifePill.SupplierService.util.StandardResponse;
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
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(404, "Error Cumming", e.getMessage()),
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
        // Log the exception for debugging purposes
       // ex.printStackTrace();

        // Provide a meaningful error message to the client
        String errorMessage = "An error occurred while processing your request. Please try again later.";

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }
}
