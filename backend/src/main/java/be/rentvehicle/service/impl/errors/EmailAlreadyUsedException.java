package be.rentvehicle.service.impl.errors;

/**
 * A class to handle EmailAlreadyUsedException exception across the whole application..
 */
public class EmailAlreadyUsedException extends RuntimeException {

    public EmailAlreadyUsedException(String email) {
        super("This '" + email + "' email is already in use!");
    }

}
