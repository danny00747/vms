package be.rentvehicle.service.impl.errors;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException(String password) {
        super("This '" + password + "' is not valid");
    }

}
