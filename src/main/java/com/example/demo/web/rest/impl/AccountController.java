package com.example.demo.web.rest.impl;

import com.example.demo.dao.UserDAO;
import com.example.demo.service.UserService;
import com.example.demo.service.dto.TeacherDTO;
import com.example.demo.service.dto.UserDTO;
import com.example.demo.service.impl.errors.EmailAlreadyUsedException;
import com.example.demo.service.impl.errors.UsernameAlreadyUsedException;
import com.example.demo.web.rest.AccountResource;
import com.example.demo.web.rest.vm.ManagedUserVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import javax.validation.Valid;

@Component
public class AccountController implements AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountController.class);

    private final UserService userService;
    private final UserDAO userDAO;

    public AccountController(UserService userService, UserDAO userDAO)  {
        this.userService = userService;
        this.userDAO = userDAO;
    }

    @Override
    public ResponseEntity<UserDTO> registerAccount(@Valid UserDTO userDTO) {
        log.debug("REST request to create a user");

        UserDTO createdUser = this.userService.registerUser(userDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
