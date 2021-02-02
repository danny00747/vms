package be.rentvehicle.security;

import be.rentvehicle.dao.UserDAO;
import be.rentvehicle.domain.User;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UserDAO userDAO;

    public DomainUserDetailsService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) {
        log.debug("Authenticating {}", username);

        // UsernameNotFoundException is never thrown because of this class CustomAuthenticationFailureHandler
        if (new EmailValidator().isValid(username, null)) {
            return userDAO.findOneWithRolesByEmailIgnoreCase(username)
                    .map(this::createSpringSecurityUser)
                    .orElseThrow(() -> new UsernameNotFoundException("User with email " + username + " was not found in the database"));
        }

        String lowercaseUsername = username.toLowerCase(Locale.ENGLISH);
        return userDAO.findOneWithRolesByUsername(lowercaseUsername)
                .map(this::createSpringSecurityUser)
                .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseUsername + " was not found in the database"));

    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(User user) {

        List<GrantedAuthority> grantedAuthorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                grantedAuthorities);
    }
}
