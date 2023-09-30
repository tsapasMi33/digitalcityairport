package be.tsapasmi33.digitalcityairport.exceptions;

public class PassengerNotFoundException extends RuntimeException {
    public PassengerNotFoundException(Long id) {
        super("Passenger with id[" + id + "] does not exist!");
    }
}
