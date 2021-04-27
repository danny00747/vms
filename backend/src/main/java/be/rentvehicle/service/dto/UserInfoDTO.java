package be.rentvehicle.service.dto;

import be.rentvehicle.config.Constants;
import be.rentvehicle.domain.Address;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public @Data class UserInfoDTO {

    private String userId;

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
    private Set<RoleDTO> userRoles;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean activated = false;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private AddressDTO addressDTO;
}
