package com.lifepill.posinventoryservice.advisor;

import com.lifepill.posinventoryservice.exception.NotFoundException;
import com.lifepill.posinventoryservice.util.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppWideExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StandardResponse> handleNotFoundException(NotFoundException e){
        return new ResponseEntity<>(
                new StandardResponse(404,"Error",e.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }
}
