package be.tsapasmi33.digitalcityairport.exceptions;

public class CancelledEventModificationException extends RuntimeException {
    public CancelledEventModificationException(Class<?> className, Long id) {
        super(className.getSimpleName() + " with id[" + id + "] is already cancelled and cannot be modified further!");
    }
}
