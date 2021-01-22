package com.example.demo.service.impl.errors;

public class UsernameAlreadyUsedException extends RuntimeException {

    public UsernameAlreadyUsedException(String username) {
        super("This '" + username + "' username is already used!");
    }

}
