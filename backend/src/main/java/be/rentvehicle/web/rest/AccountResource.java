package be.rentvehicle.web.rest;

import be.rentvehicle.security.CustomAuthenticationFailureHandler;
import be.rentvehicle.security.RolesConstants;
import be.rentvehicle.security.securityAnnotations.isAdmin;
import be.rentvehicle.security.securityAnnotations.isUsername;
import be.rentvehicle.service.dto.UserDTO;
import be.rentvehicle.service.impl.errors.EmailAlreadyUsedException;
import be.rentvehicle.service.impl.errors.UserNotFoundException;
import be.rentvehicle.service.impl.errors.UsernameAlreadyUsedException;
import be.rentvehicle.web.rest.vm.UserVM;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * REST controller for managing the current user's account.
 */
@Controller
public interface AccountResource {

    /**
     * {@code POST  /register} : register the user.
     *
     * @param userDTO the user to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new user, or with status {@code 400 (Bad Request)} if the username or email is already in use.
     * @throws EmailAlreadyUsedException    {@code 409 (CONFLICT)} if the email is already used.
     * @throws UsernameAlreadyUsedException {@code 409 (CONFLICT)} if the username is already used.
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<UserDTO> registerAccount(@Valid @RequestBody UserDTO userDTO);

    /**
     * {@code POST  /authenticate} : authenticate the user.
     *
     * @param loginVM the managed login View Model.
     * @return a {@code 401 (UNAUTHORIZED)} is sent by {@link CustomAuthenticationFailureHandler} if the credentials are incorrect.
     */
    @PostMapping("/authenticate")
    ResponseEntity<Map<String, String>> authorize(@Valid @RequestBody UserVM.LoginVM loginVM);

    /**
     * {@code PATCH /user} : Updates an existing User.
     *
     * @param updateVM the managed update View Model.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body of the updated user.
     * @throws EmailAlreadyUsedException    {@code 409 (CONFLICT)} if the email is already in use.
     * @throws UsernameAlreadyUsedException {@code 409 (CONFLICT)} if the username is already in use.
     * @throws UserNotFoundException        {@code 404 (Not Found)} if the user couldn't be returned.
     */
    @PatchMapping("/user/{usernameParam}")
    @PreAuthorize("#usernameParam == authentication.principal.username or hasAuthority(\"" + RolesConstants.ADMIN + "\")")
    ResponseEntity<UserDTO> updateUser(@PathVariable String usernameParam, @Valid @RequestBody UserVM.UpdateVM updateVM);

    /**
     * {@code GET  /users} : get all the users.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of users in body.
     */
    @GetMapping("/users")
    @isAdmin
    ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam(required = false, defaultValue = "false") boolean eagerload);

    /**
     * Gets a list of all roles.
     *
     * @return a string list of all roles.
     */
    @GetMapping("/users/roles")
    @isAdmin
    ResponseEntity<List<String>> getRoles();

    /**
     * {@code GET  /account} : get the current user.
     *
     * @return the current user.
     * @throws UserNotFoundException {@code 404 (Not Found)} if the user couldn't be returned.
     */
    @GetMapping("/account")
    ResponseEntity<UserDTO> getAccount();

    /**
     * {@code GET /user/:username} : get the "username" user.
     *
     * @param username the username of the user to find.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the "username" user, or with status {@code 404 (Not Found)}.
     * @throws UserNotFoundException {@code 404 (Not Found)} if the user couldn't be returned.
     */
    @GetMapping("/user/{username}")
    @PreAuthorize("#username == authentication.principal.username or hasAuthority(\"" + RolesConstants.ADMIN + "\")")
    ResponseEntity<UserDTO> getUser(@PathVariable("username") @isUsername String username);

    /**
     * {@code DELETE /users/:username} : delete the "username" User.
     *
     * @param username the username of the user to delete.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}.
     * @throws UserNotFoundException {@code 404 (Not Found)} if the user couldn't be returned.
     */
    @DeleteMapping("/user/{username}")
    @isAdmin
    ResponseEntity<Map<String, String>> deleteUser(@PathVariable String username);
}
