package com.crudtech.cadastrin.helper;

import org.springframework.stereotype.Service;

@Service
public class UserHelper {

    public boolean isPasswordValid(String password){
        return password.length() >= 6;
    }
}
