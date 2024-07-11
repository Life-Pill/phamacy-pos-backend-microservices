package com.lifepill.employeeService.advisor;

import com.lifepill.employeeService.util.StandardResponse;
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
        String errorMessage;

        // Customize error messages based on different HTTP status codes
        switch (httpStatus) {
            case NOT_FOUND:
                errorMessage = "The resource you are looking for is not found.";
                break;
            case BAD_REQUEST:
                errorMessage = "Bad request. Please check your request parameters.";
                break;
            case UNAUTHORIZED:
                errorMessage = "Unauthorized access. Please authenticate first.";
                break;
            default:
                errorMessage = "An unexpected error occurred.";
        }


        return new ResponseEntity<>(
                new StandardResponse(httpStatus.value(), "Error", errorMessage),
                httpStatus
        );
    }
}
