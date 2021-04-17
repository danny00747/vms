package be.rentvehicle.service.impl.errors;

public class CarNotFoundException extends RuntimeException {
    public CarNotFoundException(String id) {
        super("No car was found with this id :" + id);
    }
}
