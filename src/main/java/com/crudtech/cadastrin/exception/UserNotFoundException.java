package com.crudtech.cadastrin.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(Long id){
        super("Could not find user: " + id);
    }
    
    public UserNotFoundException(){
        super("Could not find user");
    }
}
