package be.tsapasmi33.digitalcityairport.exceptions;

public class AirplaneNotFoundException extends RuntimeException {
    public AirplaneNotFoundException(Long id) {
        super("Airplane with id[" + id + "] does not exist!");
    }
}
