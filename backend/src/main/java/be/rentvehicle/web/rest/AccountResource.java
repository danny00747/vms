package be.rentvehicle.web.rest;

import be.rentvehicle.config.TwilioConfiguration;
import be.rentvehicle.security.CustomAuthenticationFailureHandler;
import be.rentvehicle.security.RolesConstants;
import be.rentvehicle.security.jwt.JWTFilter;
import be.rentvehicle.security.jwt.TokenProvider;
import be.rentvehicle.security.securityAnnotations.isAdmin;
import be.rentvehicle.security.securityAnnotations.isUsername;
import be.rentvehicle.service.MailService;
import be.rentvehicle.service.UserService;
import be.rentvehicle.service.dto.UserDTO;
import be.rentvehicle.service.impl.errors.EmailAlreadyUsedException;
import be.rentvehicle.service.impl.errors.UserNotFoundException;
import be.rentvehicle.service.impl.errors.UsernameAlreadyUsedException;
import be.rentvehicle.web.rest.vm.UserVM;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
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

import java.security.SecureRandom;
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

    private final MailService mailService;

    private final TwilioConfiguration twilioConfiguration;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AccountResource(UserService userService, TokenProvider tokenProvider, MailService mailService, TwilioConfiguration twilioConfiguration, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.mailService = mailService;
        this.twilioConfiguration = twilioConfiguration;
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
    public ResponseEntity<UserDTO> registerAccount(@RequestBody UserDTO userDTO){
        log.debug("REST request to create a user");
        UserDTO createdUser = this.userService.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    /**
     * {@code POST  /authenticate} : authenticate the user.
     *
     * @param loginVM the managed login View Model.
     * @return a {@code 401 (UNAUTHORIZED)} is sent by {@link CustomAuthenticationFailureHandler} if the credentials are incorrect.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, String>> authorize(@RequestBody UserVM.LoginVM loginVM){
        log.debug("REST request to authenticate : {}", loginVM.pseudo());
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginVM.pseudo(), loginVM.password());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        /*
         // mailService.sendMail();

         SecureRandom secureRandom = new SecureRandom();
         int myCode = secureRandom.nextInt(9000000) + 1000000;


         PhoneNumber to = new PhoneNumber("+32485713601");
         PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
         String message = "\n[RentVehicle] Your verification code is : " + myCode;
         MessageCreator creator = Message.creator(to, from, message);
         creator.create();
         log.info("Send sms {}", "sms sent with code :" + myCode);
         */
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(httpHeaders)
                .body(Map.of("message", "successful authentication !", "token", "Bearer " + jwt));
    }

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
    public ResponseEntity<UserDTO> updateUser(@PathVariable("usernameParam") String usernameParam, @RequestBody UserVM.UpdateVM updateVM){
        log.debug("REST request to upfate User : {}", usernameParam);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService
                        .updateUser(usernameParam, updateVM.username(), updateVM.email())
                        .map(UserDTO::new)
                        .orElseThrow(() -> new UserNotFoundException(usernameParam)));
    }

    /**
     * {@code GET  /users} : get all the users.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of users in body.
     */
    @GetMapping("/users")
    @isAdmin
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam(required = false, defaultValue = "false") boolean eagerload){
        log.debug("REST request to get all users");
        List<UserDTO> users = this.userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    /**
     * Gets a list of all roles.
     *
     * @return a string list of all roles.
     */
    @GetMapping("/users/roles")
    @isAdmin
    public ResponseEntity<List<String>> getRoles(){
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
    public ResponseEntity<UserDTO> getAccount(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService
                        .getUserWithRoles()
                        .map(UserDTO::new)
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
    public ResponseEntity<UserDTO> getUser(@PathVariable("username") @isUsername String username){
        log.debug("REST request to get User : {}", username);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUserWithRolesByUsername(username)
                        .map(UserDTO::new)
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
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable String username){
        log.debug("REST request to delete User : {}", username);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("message", userService.deleteUser(username)
                        .orElseThrow(() -> new UserNotFoundException(username))));
    }
}
