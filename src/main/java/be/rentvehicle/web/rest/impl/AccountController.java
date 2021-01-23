package be.rentvehicle.web.rest.impl;

import be.rentvehicle.dao.UserDAO;
import be.rentvehicle.service.UserService;
import be.rentvehicle.service.dto.UserDTO;
import be.rentvehicle.web.rest.AccountResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;

@Component
public class AccountController implements AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountController.class);

    private final UserService userService;
    private final UserDAO userDAO;

    public AccountController(UserService userService, UserDAO userDAO) {
        this.userService = userService;
        this.userDAO = userDAO;
    }

    @Override
    public ResponseEntity<UserDTO> registerAccount(@Valid UserDTO userDTO) {
        log.debug("REST request to create a user");

        UserDTO createdUser = this.userService.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @Override
    public ResponseEntity<List<UserDTO>> getAllUsers(boolean eagerload) {
        log.debug("REST request to get all users");
        List<UserDTO> users = this.userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @Override
    public ResponseEntity<List<String>> getRoles() {
        log.debug("REST request to get all roles");
        List<String> roles = this.userService.getRoles();
        return ResponseEntity.status(HttpStatus.OK).body(roles);
    }
}
