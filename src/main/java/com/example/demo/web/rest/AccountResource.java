package com.example.demo.web.rest;

import com.example.demo.service.dto.UserDTO;
import com.example.demo.service.impl.errors.EmailAlreadyUsedException;
import com.example.demo.web.rest.errors.InvalidPasswordException;
import com.example.demo.web.rest.errors.usernameAlreadyUsedException;
import com.example.demo.web.rest.vm.ManagedUserVM;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import javax.validation.Valid;

/**
 * REST controller for managing the current user's account.
 */
@Controller
public interface AccountResource {

    /**
     * {@code POST  /register} : register the user.
     *
     * @param userDTO the managed user View Model.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is incorrect.
     * @throws EmailAlreadyUsedException {@code 409 (CONFLICT)} if the email is already used.
     * @throws usernameAlreadyUsedException {@code 409 (CONFLICT)} if the username is already used.
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<UserDTO> registerAccount(@Valid @RequestBody UserDTO userDTO);
}
