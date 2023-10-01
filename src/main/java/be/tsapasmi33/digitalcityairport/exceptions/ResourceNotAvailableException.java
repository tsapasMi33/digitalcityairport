package be.tsapasmi33.digitalcityairport.exceptions;

public class ResourceNotAvailableException extends RuntimeException {
    public ResourceNotAvailableException(Class<?> className, Long id, String reason) {
        super(className.getSimpleName() + " with id[" + id + "] is not available " + reason + "!");
    }
}
