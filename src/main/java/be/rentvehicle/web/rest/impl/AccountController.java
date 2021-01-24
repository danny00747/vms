package be.rentvehicle.web.rest.impl;

import be.rentvehicle.security.jwt.JWTFilter;
import be.rentvehicle.security.jwt.TokenProvider;
import be.rentvehicle.service.UserService;
import be.rentvehicle.service.dto.UserDTO;
import be.rentvehicle.service.impl.errors.UserNotFoundException;
import be.rentvehicle.web.rest.AccountResource;
import be.rentvehicle.web.rest.vm.LoginVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Component
public class AccountController implements AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountController.class);

    private final UserService userService;

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AccountController(UserService userService, TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @Override
    public ResponseEntity<UserDTO> registerAccount(@Valid UserDTO userDTO) {
        log.debug("REST request to create a user");

        UserDTO createdUser = this.userService.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @Override
    public ResponseEntity<Map<String, String>> authorize(@Valid LoginVM loginVM) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginVM.getPseudo(), loginVM.getPassword());

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

    @Override
    public ResponseEntity<UserDTO> getAccount() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService
                        .getUserWithRoles()
                        .map(UserDTO::new)
                        .orElseThrow(() -> new UserNotFoundException("User could not be found")));

    }

    @Override
    public ResponseEntity<UserDTO> getUser(@Valid String username) {
        log.debug("REST request to get User : {}", username);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUserWithRolesByUsername(username)
                        .map(UserDTO::new)
                        .orElseThrow(() -> new UserNotFoundException(username)));
    }
}
