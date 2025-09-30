package com.claudiordese.kafka.layers.rest;

import com.claudiordese.kafka.exceptions.response.ErrorResponse;
import com.claudiordese.kafka.exceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(UnauthorizedException unauthorizedException) {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setMessage(unauthorizedException.getMessage());
        errorResponse.setStatus(403);
        errorResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}
