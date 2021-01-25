package be.rentvehicle.service.impl.errors;

/**
 * A class to handle UserNotFoundException exception across the whole application..
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("This user '" + username + "' could not be found !");
    }
}
