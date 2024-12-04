package com.ozkan.couriertracking.application.exception;

public class DuplicateStoreEntryException extends RuntimeException {
    public DuplicateStoreEntryException(String message) {
        super(message);
    }
}
