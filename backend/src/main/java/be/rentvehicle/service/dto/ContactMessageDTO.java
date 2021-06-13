package be.rentvehicle.service.dto;

import be.rentvehicle.config.Constants;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public  @Data class ContactMessageDTO {

    @NotNull(message = "first name is a required field.")
    @Size(min = 4, max = 32, message = "A first name length must be between 4 and 32.")
    private String firstName;

    @NotNull(message = "last name is a required field.")
    @Size(min = 4, max = 32, message = "A last name length must be between 4 and 32.")
    private String lastName;

    @NotNull(message = "phone number is a required field.")
    @Size(min = 9, max = 12, message = "A phone number length must be between 10 and 12.")
    private String phoneNumber;

    @Pattern(regexp = Constants.EMAIL_REGEX, message = "Please provide a valid email")
    @NotNull(message = "userEmail is a required field.")
    @Size(min = 5, max = 254)
    private String email;

    @NotNull(message = "message is a required field.")
    @Size(min = 4, max = 512, message = "A message length must be between 4 and 512.")
    private String message;
}
