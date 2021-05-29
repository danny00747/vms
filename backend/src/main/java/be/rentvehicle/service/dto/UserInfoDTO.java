package be.rentvehicle.service.dto;
;
import be.rentvehicle.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

/**
 * A DTO for the {@link User} entity.
 */

public @Data class UserInfoDTO implements Serializable {

    private String userId;

    @NotNull(message = "username is a required field.")
    @Size(min = 4, max = 32, message = "A username length must be between 4 and 32.")
    private String username;

    @NotNull(message = "userEmail is a required field.")
    @Size(min = 5, max = 254)
    private String userEmail;

    @NotNull(message = "phone number is a required field.")
    @Size(min = 9, max = 12, message = "A phone number length must be between 10 and 100.")
    private String phoneNumber;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 4, max = 100, message = "A password length must be between 4 and 100.")
    @NotNull(message = "password is a required field.")
    private String password;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<RoleDTO> userRoles;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean activated;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant createdAt;

    private AddressDTO addressDTO;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BookingDTO bookingDTO;
}
