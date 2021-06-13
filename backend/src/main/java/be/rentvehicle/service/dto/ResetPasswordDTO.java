package be.rentvehicle.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public @Data class ResetPasswordDTO {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 36, max = 36, message = "The key length must be 36.")
    @NotNull(message = "key is a required field.")
    private String key;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 4, max = 100, message = "A password length must be between 4 and 100.")
    @NotNull(message = "password is a required field.")
    private String newPassword;
}
