package com.ozkan.couriertracking.infrastructure.exception;

import com.ozkan.couriertracking.application.exception.CourierNotFoundException;
import com.ozkan.couriertracking.application.exception.DuplicateStoreEntryException;
import com.ozkan.couriertracking.application.exception.OutOfStoreRadiusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {CourierNotFoundException.class, OutOfStoreRadiusException.class, DuplicateStoreEntryException.class})
    public ResponseEntity<String> handleNotFoundException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }
}
