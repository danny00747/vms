package be.rentvehicle.web.rest;

import be.rentvehicle.security.CustomAuthenticationFailureHandler;
import be.rentvehicle.security.RolesConstants;
import be.rentvehicle.security.jwt.JWTFilter;
import be.rentvehicle.security.jwt.TokenProvider;
import be.rentvehicle.security.securityAnnotations.isAdmin;
import be.rentvehicle.security.securityAnnotations.isUsername;
import be.rentvehicle.service.UserService;
import be.rentvehicle.service.dto.ContactMessageDTO;
import be.rentvehicle.service.dto.ResetPasswordDTO;
import be.rentvehicle.service.dto.UserInfoDTO;
import be.rentvehicle.service.impl.errors.*;
import be.rentvehicle.web.rest.vm.UserVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * REST controller for managing the current user's account.
 */
@Controller
@Validated
public class AccountResource extends BaseRestController {

    private static final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final UserService userService;

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AccountResource(UserService userService, TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

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
    public ResponseEntity<Map<String, String>> registerAccount(@RequestBody @Valid UserInfoDTO userDTO) {
        log.debug("REST request to create a user");
        UserInfoDTO createdUser = this.userService.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "A verification key has been sent to " + createdUser.getUserEmail()));
    }

    /**
     * {@code GET  /activate/{username}} : activate the registered user.
     *
     * @param username the user to activate key.
     * @throws UserNotFoundException {@code 404 (Not Found)} if the user couldn't be activated.
     */
    @GetMapping("/activate/{username}")
    public ResponseEntity<Map<String, String>> activateAccount(@PathVariable("username") String username) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("message", userService.activateRegistration(username)
                        .orElseThrow(() -> new UserNotFoundException(username))));
    }

    /**
     * {@code POST  /authenticate} : authenticate the user.
     *
     * @param loginVM the managed login View Model.
     * @return a {@code 401 (UNAUTHORIZED)} is sent by {@link CustomAuthenticationFailureHandler} if the credentials are incorrect.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, String>> authorize(@RequestBody UserVM.LoginVM loginVM) {
        log.debug("REST request to authenticate : {}", loginVM.pseudo());
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginVM.pseudo(), loginVM.password());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(httpHeaders)
                .body(Map.of("message", "successful authentication !", "token", "Bearer " + jwt));
    }

    /**
     * {@code PATCH /user/:usernameParam} : Updates an existing User.
     *
     * @param userDTO the managed update View Model.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body of the updated user.
     * @throws EmailAlreadyUsedException    {@code 409 (CONFLICT)} if the email is already in use.
     * @throws UsernameAlreadyUsedException {@code 409 (CONFLICT)} if the username is already in use.
     * @throws UserNotFoundException        {@code 404 (Not Found)} if the user couldn't be returned.
     */
    @PatchMapping("/user/{usernameParam}")
    @PreAuthorize("#usernameParam == authentication.principal.username or hasAuthority(\"" + RolesConstants.ADMIN + "\")")
    public ResponseEntity<UserInfoDTO> updateUser(@PathVariable("usernameParam") String usernameParam, @RequestBody UserInfoDTO userDTO) {
        log.debug("REST request to upfate User : {}", usernameParam);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService
                        .updateUser(usernameParam, userDTO)
                        .orElseThrow(() -> new UserNotFoundException(usernameParam)));
    }

    /**
     * {@code GET  /users} : get all the users.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of users in body.
     */
    @GetMapping("/users")
    @isAdmin
    public ResponseEntity<List<UserInfoDTO>> getAllUsers() {
        log.debug("REST request to get all users");
        List<UserInfoDTO> users = this.userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    /**
     * Gets a list of all roles.
     *
     * @return a string list of all roles.
     */
    @GetMapping("/users/roles")
    @isAdmin
    public ResponseEntity<List<String>> getRoles() {
        log.debug("REST request to get all roles");
        List<String> roles = this.userService.getRoles();
        return ResponseEntity.status(HttpStatus.OK).body(roles);
    }

    /**
     * {@code GET  /account} : get the current user.
     *
     * @return the current user.
     * @throws UserNotFoundException {@code 404 (Not Found)} if the user couldn't be returned.
     */
    @GetMapping("/account")
    public ResponseEntity<UserInfoDTO> getAccount() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService
                        .getUserWithJwt()
                        .orElseThrow(() -> new UserNotFoundException("User could not be found")));
    }

    /**
     * {@code GET /user/:username} : get the "username" user.
     *
     * @param username the username of the user to find.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the "username" user, or with status {@code 404 (Not Found)}.
     * @throws UserNotFoundException {@code 404 (Not Found)} if the user couldn't be returned.
     */
    @GetMapping("/user/{username}")
    @PreAuthorize("#username == authentication.principal.username or hasAuthority(\"" + RolesConstants.ADMIN + "\")")
    public ResponseEntity<UserInfoDTO> getUser(@PathVariable("username") @isUsername String username) {
        log.debug("REST request to get User : {}", username);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getByUsername(username)
                        .orElseThrow(() -> new UserNotFoundException(username)));
    }

    /**
     * {@code DELETE /users/:username} : delete the "username" User.
     *
     * @param username the username of the user to delete.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}.
     * @throws UserNotFoundException {@code 404 (Not Found)} if the user couldn't be returned.
     */
    @DeleteMapping("/user/{username}")
    @isAdmin
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable String username) {
        log.debug("REST request to delete User : {}", username);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("message", userService.deleteUser(username)
                        .orElseThrow(() -> new UserNotFoundException(username))));
    }

    /**
     * {@code DELETE /user/bulk/delete} : delete Users by id.
     *
     * @param userIds the ids of users to delete.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}.
     */
    @DeleteMapping("/user/bulk/delete")
    @isAdmin
    public ResponseEntity<Map<String, String>> bulkDeleteUsers(@RequestBody List<String> userIds) {
        log.debug("REST request to delete multiple Users : {}", userIds);
        String message = userIds.size() <= 1
                ? "No user was found with this id : " + String.join(",", userIds)
                : "No users was found with these ids : " + String.join(",", userIds);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("message", userService.bulkDeleteUsers(userIds)
                        .orElseThrow(() -> new ResourceNotFoundException(message))));
    }

    /**
     * {@code GET  /verifyPhone} : verify a phone number.
     *
     * @param code the code to verify.
     * @throws AccountResourceException {@code 404 (Not Found)} if the user with this verificationCode couldn't be found.
     */
    @GetMapping("/verifyPhone")
    public ResponseEntity<Map<String, String>> verifyPhoneNumber(@RequestParam(value = "code") Integer code) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("message", userService.verifyPhoneNumber(code)
                        .orElseThrow(() -> new AccountResourceException("No user was found with this verification code: " + code))));
    }

    /**
     * {@code POST  /contact} : send admin a customer message.
     *
     * @param contactMessageDTO containing a customer message..
     */
    @PostMapping("/contact")
    public ResponseEntity<Map<String, String>> contactMessage(@RequestBody ContactMessageDTO contactMessageDTO) {
        log.debug("sending a customer message to admin {}", contactMessageDTO);
        userService.sendContactMessage(contactMessageDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("message", "Your message was sent successfully !"));
    }

    /**
     * {@code POST   /reset-password/init} : Send an email to reset the password of the user.
     *
     * @param mail the mail of the user.
     * @throws AccountResourceException {@code 404 (Not Found)} if the user with this verificationCode couldn't be found.
     */
    @PostMapping("/reset-password/init")
    public ResponseEntity<Map<String, String>> requestPasswordReset(@RequestBody String mail) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("message", userService.requestPasswordReset(mail)
                        .orElseThrow(() -> new AccountResourceException("No user was found with this email: " + mail))));
    }

    /**
     * {@code POST   /reset-password/finish} : Finish to reset the password of the user.
     *
     * @param resetPasswordDTO the generated key and the new password.
     * @throws AccountResourceException {@code 404 (Not Found)} if the password could not be reset since the key is invalid.
     */
    @PostMapping("/reset-password/finish")
    public ResponseEntity<Map<String, String>> finishPasswordReset(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("message", userService.completePasswordReset(resetPasswordDTO)
                        .orElseThrow(() -> new AccountResourceException("No user was found for this reset key: " + resetPasswordDTO.getKey()))));
    }

    /**
     * {@code GET  /verifyEmail} : verify an email address.
     *
     * @param key the key to verify.
     * @throws AccountResourceException {@code 404 (Not Found)} if the user with this verificationCode couldn't be found.
     */
    @GetMapping("/verifyEmail")
    public ResponseEntity<Map<String, String>> verifyEmail(@RequestParam(value = "key") String key) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("message", userService.verifyEmail(key)
                        .orElseThrow(() -> new AccountResourceException("No user was found with this key: " + key))));
    }
}
