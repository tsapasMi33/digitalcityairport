package be.tsapasmi33.digitalcityairport.exceptions;

public class AirportNotFoundException extends RuntimeException {
    public AirportNotFoundException(Long id) {
        super("Airport with id[" + id + "] does not exist!");
    }

    public AirportNotFoundException(String message) {
        super(message);
    }
}
