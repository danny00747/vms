package be.rentvehicle.service.dto;

import be.rentvehicle.config.Constants;
import be.rentvehicle.domain.Roles;
import be.rentvehicle.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record UserDTOO(

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        UUID userId,

        @NotNull(message = "username is a required field.")
        @Size(min = 4, max = 32, message = "A username length must be between 4 and 32.") String username,

        @Pattern(regexp = Constants.EMAIL_REGEX, message = "Please provide a valid email")
        @Size(min = 5, max = 254)
        String userEmail,

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @Size(min = 4, max = 100, message = "A password length must be between 4 and 100.")
        @NotNull(message = "password is a required field.")
        String password,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Set<String> userRoles) {

}


