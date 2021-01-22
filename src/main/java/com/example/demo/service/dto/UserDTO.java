package com.example.demo.service.dto;

import com.example.demo.config.Constants;
import com.example.demo.domain.Roles;
import com.example.demo.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mapstruct.Mapper;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A DTO for the {@link User} entity.
 */
public class UserDTO implements Serializable {

    private int userId;

    @NotBlank
    @Pattern(regexp = Constants.USERNAME_REGEX)
    @Size(min = 4, max = 25)
    private String username;

    @Email
    @Size(min = 5, max = 254)
    private String userEmail;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    private String password;

    private Set<Roles> roles = new HashSet<>();

    public UserDTO() {
        // Empty constructor needed for Jackson.
    }


        public UserDTO(User user) {
        this.userId = user.getId();
        this.userEmail = user.getEmail();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.roles = user.getRoles();
    }


    /*
       public UserDTO(int userId, String username, String userEmail, String password) {
        this.userId = userId;
        this.username = username;
        this.userEmail = userEmail;
        this.password = password;
    }
     */


    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserDTO{" +
                "userId='" + userId + '\'' +
                ", email='" + userEmail + '\'' +
                ", username='" + username + '\'' +
                ", roles=" + roles +
                "}";
    }
}
