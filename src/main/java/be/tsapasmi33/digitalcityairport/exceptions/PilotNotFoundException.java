package be.tsapasmi33.digitalcityairport.exceptions;

public class PilotNotFoundException extends RuntimeException {
    public PilotNotFoundException(Long id) {
        super("Pilot with id[" + id + "] does not exist!");
    }
}
