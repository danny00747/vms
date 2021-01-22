package com.example.demo.service.impl.errors;

public class EmailAlreadyUsedException extends RuntimeException {

    public EmailAlreadyUsedException(String email) {
        super("This '" + email + "' email is already in use!");
    }

}
