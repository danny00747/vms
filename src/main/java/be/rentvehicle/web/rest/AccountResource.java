package be.rentvehicle.web.rest;

import be.rentvehicle.service.dto.UserDTO;
import be.rentvehicle.service.impl.errors.EmailAlreadyUsedException;
import be.rentvehicle.service.impl.errors.UsernameAlreadyUsedException;
import be.rentvehicle.service.impl.errors.InvalidPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
     * @throws UsernameAlreadyUsedException {@code 409 (CONFLICT)} if the username is already used.
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<UserDTO> registerAccount(@Valid @RequestBody UserDTO userDTO);

    /**
     * {@code GET  /users} : get all the users.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of users in body.
     */
    @GetMapping("/users")
    ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam(required = false, defaultValue = "false") boolean eagerload);

    /**
     * Gets a list of all roles.
     * @return a string list of all roles.
     */
    @GetMapping("/users/roles")
    ResponseEntity<List<String>> getRoles();
}
