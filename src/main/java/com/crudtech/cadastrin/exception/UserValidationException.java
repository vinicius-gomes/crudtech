package com.crudtech.cadastrin.exception;

public class UserValidationException extends RuntimeException {

    public UserValidationException(String message) {
        super("Validation error: " + message);
    }

    public UserValidationException() {
        super("Validation error");
    }
}