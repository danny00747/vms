package be.rentvehicle.web.rest.vm;

import be.rentvehicle.config.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


/*
 * View Model object for storing a user's credentials used for authentification.
 */

public class LoginVM {

    @NotNull(message = "pseudo is a required field.")
    @Size(min = 4, max = 32, message = "'A username length must be between 4 and 32.")
    private String pseudo;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "password is a required field.")
    private String password;

    public String getPseudo() {
        return pseudo;
    }

    public String getPassword() {
        return password;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginVM{" +
                "username='" + pseudo + '\'' +
                '}';
    }
}
