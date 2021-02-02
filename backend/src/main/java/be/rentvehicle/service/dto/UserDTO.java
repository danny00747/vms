package be.rentvehicle.service.dto;

import be.rentvehicle.config.Constants;
import be.rentvehicle.domain.Roles;
import be.rentvehicle.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * A DTO for the {@link User} entity.
 */
public class UserDTO implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID userId;

    @NotNull(message = "username is a required field.")
    @Size(min = 4, max = 32, message = "A username length must be between 4 and 32.")
    private String username;

    @Pattern(regexp = Constants.EMAIL_REGEX, message = "Please provide a valid email")
    @Size(min = 5, max = 254)
    private String userEmail;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 4, max = 100, message = "A password length must be between 4 and 100.")
    @NotNull(message = "password is a required field.")
    private String password;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<String> userRoles = new HashSet<>();

    public UserDTO() {
        // Empty constructor needed for Jackson.
    }

    public UserDTO(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.userEmail = user.getEmail();
        this.userRoles = user.getRoles()
                .stream()
                .map(Roles::getName)
                .collect(Collectors.toSet());
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<String> userRoles) {
        this.userRoles = userRoles;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserDTO{" +
                "userId='" + userId + '\'' +
                ", email='" + userEmail + '\'' +
                ", username='" + username + '\'' +
                ", userRoles=" + userRoles +
                "}";
    }
}
