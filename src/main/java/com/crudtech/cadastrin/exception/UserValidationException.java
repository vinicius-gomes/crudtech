package com.crudtech.cadastrin.exception;

public class UserValidationException extends RuntimeException {

    public UserValidationException(){
        super("Unable to validate user. Please, verify your credentials.");
    }
}
