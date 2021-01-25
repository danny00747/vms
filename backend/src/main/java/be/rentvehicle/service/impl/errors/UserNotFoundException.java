package be.rentvehicle.service.impl.errors;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("This user '" + username + "' could not be found !");
    }
}
