package com.lifepill.posorderservice.advisor;

import com.lifepill.posorderservice.util.StandardResponse;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<StandardResponse> handleFeignException(FeignException e) {
        // Extract the error message from FeignException based on its status code
        HttpStatus httpStatus = HttpStatus.valueOf(e.status());
        String errorMessage = switch (httpStatus) {
            case NOT_FOUND -> "The resource you are looking for is not found.";
            case BAD_REQUEST -> "Bad request. Please check your request parameters.";
            case UNAUTHORIZED -> "Unauthorized access. Please authenticate first.";
            default -> "An unexpected error occurred.";
        };

        return new ResponseEntity<>(
                new StandardResponse(httpStatus.value(), "Error", errorMessage),
                httpStatus
        );
    }
}
