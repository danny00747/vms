package be.rentvehicle.service.impl.errors;

/**
 * A class to handle UsernameAlreadyUsedException exception across the whole application..
 */
public class UsernameAlreadyUsedException extends RuntimeException {

    public UsernameAlreadyUsedException(String username) {
        super("This '" + username + "' username is already used!");
    }

}
