package be.rentvehicle.web.rest.vm;

import be.rentvehicle.config.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public enum UserVM {
    ;

    public static record UpdateVM(

            @NotNull(message = "username is a required field.")
            @Size(min = 4, max = 32, message = "'A username length must be between 4 and 32.")
            String username,

            @Pattern(regexp = Constants.EMAIL_REGEX, message = "Please provide a valid email")
            @NotNull(message = "email is a required field.")
            @Size(min = 5, max = 254)
            String email) {

        public UpdateVM(String username, String email) {
            this.username = username;
            this.email = email;
        }
    }

    public static record LoginVM(

            @NotNull(message = "pseudo is a required field.")
            @Size(min = 4, max = 32, message = "'A pseudo length must be between 4 and 32.")
            String pseudo,

            @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
            @NotNull(message = "password is a required field.")
            String password) {

        public LoginVM(String pseudo, String password) {
            this.password = password;
            this.pseudo = pseudo;
        }
    }
}
