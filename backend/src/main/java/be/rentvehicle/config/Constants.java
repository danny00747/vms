package be.rentvehicle.config;

/**
 * Regex for constants across the whole application.
 */
public final class Constants {

    // Regex for acceptable variables
    public static final String USERNAME_REGEX = "^[a-zA-Z0-9]([._](?![._])|[a-zA-Z0-9]){6,18}[a-zA-Z0-9]$";
    public static final String EMAIL_REGEX = "^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    public static final String UUID_REGEX = "^[0-9A-F]{8}-[0-9A-F]{4}-4[0-9A-F]{3}-[89AB][0-9A-F]{3}-[0-9A-F]{12}$";

    private Constants() {
    }
}
