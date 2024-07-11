package com.lifePill.posbranchservice.advisor;

import com.lifePill.posbranchservice.exception.NotFoundException;
import com.lifePill.posbranchservice.util.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppWideExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StandardResponse> handleNotFoundException(NotFoundException e){
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(404,"An unexpected error occurred.",e.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }
}
