package be.rentvehicle.security;

import be.rentvehicle.VmsApplication;
import be.rentvehicle.domain.User;
import be.rentvehicle.dao.UserDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integrations tests for {@link DomainUserDetailsService}.
 */
@SpringBootTest(classes = VmsApplication.class)
@Transactional
public class DomainUserDetailsServiceIT {

    private static final String USER_ONE_LOGIN = "testuser1";
    private static final String USER_ONE_EMAIL = "testuser1@gmail.com";
    private static final String USER_TWO_LOGIN = "testuser2";
    private static final String USER_TWO_EMAIL = "testuser2@gmail.com";
    private static final String USER_THREE_LOGIN = "testuser3";
    private static final String USER_THREE_EMAIL = "testuser3@gmail.com";

    @Autowired
    private UserDAO userDAO;

    @Qualifier("userDetailsService")
    @Autowired
    private UserDetailsService domainUserDetailsService;

    @BeforeEach
    public void init() {
        User userOne = new User();
        userOne.setUsername(USER_ONE_LOGIN);
        userOne.setPassword("toto");
        userOne.setEmail(USER_ONE_EMAIL);
        userDAO.save(userOne);

        User userTwo = new User();
        userTwo.setUsername(USER_TWO_LOGIN);
        userTwo.setPassword("toto");
        userTwo.setEmail(USER_TWO_EMAIL);
        userDAO.save(userTwo);

        User userThree = new User();
        userThree.setUsername(USER_THREE_LOGIN);
        userThree.setPassword("toto");
        userThree.setEmail(USER_THREE_EMAIL);
        userDAO.save(userThree);
    }

    @Test
    @DisplayName("assert that user can be found by username")
    public void assertThatUserCanBeFoundByUsername() {
        UserDetails userDetails = domainUserDetailsService.loadUserByUsername(USER_ONE_LOGIN);
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(USER_ONE_LOGIN);
    }

    @Test
    @DisplayName("assert That User Can Be Found By Username IgnoreCase")
    public void assertThatUserCanBeFoundByUsernameIgnoreCase() {
        UserDetails userDetails = domainUserDetailsService.loadUserByUsername(USER_ONE_LOGIN.toUpperCase());
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(USER_ONE_LOGIN);
    }


    @Test
    @DisplayName("assert that user can be found By email")
    public void assertThatUserCanBeFoundByEmail() {
        UserDetails userDetails = domainUserDetailsService.loadUserByUsername(USER_TWO_EMAIL);
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(USER_TWO_LOGIN);
    }

    @Test
    @DisplayName("assert that user can be found by email ignoreCase")
    public void assertThatUserCanBeFoundByEmailIgnoreCase() {
        UserDetails userDetails = domainUserDetailsService.loadUserByUsername(USER_TWO_EMAIL.toUpperCase());
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(USER_TWO_LOGIN);
    }

    @Test
    @DisplayName("assert that email is prioritized over username")
    public void assertThatEmailIsPrioritizedOverUsername() {
        UserDetails userDetails = domainUserDetailsService.loadUserByUsername(USER_ONE_EMAIL);
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(USER_ONE_LOGIN);
    }
}
