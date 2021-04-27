package be.rentvehicle.service.dto;

import be.rentvehicle.config.Constants;
import be.rentvehicle.domain.Address;
import be.rentvehicle.domain.Roles;
import be.rentvehicle.domain.Town;
import be.rentvehicle.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * A DTO for the {@link User} entity.
 */
@NoArgsConstructor
public @Data class UserDTO implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID userId;

    @NotNull(message = "username is a required field.")
    @Size(min = 4, max = 32, message = "A username length must be between 4 and 32.")
    private String username;

    @Pattern(regexp = Constants.EMAIL_REGEX, message = "Please provide a valid email")
    @NotNull(message = "userEmail is a required field.")
    @Size(min = 5, max = 254)
    private String userEmail;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 4, max = 100, message = "A password length must be between 4 and 100.")
    @NotNull(message = "password is a required field.")
    private String password;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<String> userRoles;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean activated = false;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant createdAt;

    @Valid
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Address address;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Map<String, String> userAdress;

    @Valid
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Town town;

    public UserDTO(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.userEmail = user.getEmail();
        this.activated = user.isActivated();
        this.createdAt = user.getCreatedAt();
        this.userAdress = Map.of(
                "address", user.getAddress().getRoad() + ", " + user.getAddress().getHouseNumber(),
                "town", user.getAddress().getTown().getName()
        );
        this.userRoles = user.getRoles()
                .stream()
                .map(Roles::getName)
                .collect(Collectors.toSet());
    }
}
