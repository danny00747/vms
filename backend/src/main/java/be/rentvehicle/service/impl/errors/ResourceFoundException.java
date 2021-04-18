package be.rentvehicle.service.impl.errors;

public class ResourceFoundException extends RuntimeException {
    public ResourceFoundException(String message) {
        super(message);
    }
}
