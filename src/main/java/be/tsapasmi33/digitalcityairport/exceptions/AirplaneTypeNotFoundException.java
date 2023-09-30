package be.tsapasmi33.digitalcityairport.exceptions;

public class AirplaneTypeNotFoundException extends RuntimeException {
    public AirplaneTypeNotFoundException(Long id) {
        super("Airplane Type with id[" + id + "] does not exist!");
    }
}
