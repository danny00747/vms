package be.rentvehicle.service.dto;

import be.rentvehicle.config.Constants;
import be.rentvehicle.domain.Address;
import be.rentvehicle.domain.Booking;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public @Data class UserInfoDTO implements Serializable {

    private String userId;

    @NotNull(message = "username is a required field.")
    @Size(min = 4, max = 32, message = "A username length must be between 4 and 32.")
    private String username;

    @NotNull(message = "userEmail is a required field.")
    @Size(min = 5, max = 254)
    private String userEmail;

    @NotNull(message = "phone number is a required field.")
    @Size(min = 12, max = 12, message = "A phone number length must be between 12.")
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
