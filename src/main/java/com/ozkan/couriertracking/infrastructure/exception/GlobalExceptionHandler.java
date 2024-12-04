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

    @ExceptionHandler(value = {CourierNotFoundException.class})
    public ResponseEntity<String> handleNotFoundException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(value = {OutOfStoreRadiusException.class})
    public ResponseEntity<String> handleOutOfStoreRadiusException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = {DuplicateStoreEntryException.class})
    public ResponseEntity<String> handleDuplicateStoreEntryException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }
}
