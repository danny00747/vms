package com.example.demo.web.rest.errors;

public class usernameAlreadyUsedException extends BadRequestAlertException {

    public usernameAlreadyUsedException() {
        super(ErrorConstants.LOGIN_ALREADY_USED_TYPE, "username already used!", "userManagement", "userexists");
    }
}
