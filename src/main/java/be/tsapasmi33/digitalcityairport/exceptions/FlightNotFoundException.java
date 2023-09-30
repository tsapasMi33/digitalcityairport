package be.tsapasmi33.digitalcityairport.exceptions;

public class FlightNotFoundException extends RuntimeException {
    public FlightNotFoundException(Long id) {
        super("Flight with id[" + id + "] does not exist!");
    }
}
