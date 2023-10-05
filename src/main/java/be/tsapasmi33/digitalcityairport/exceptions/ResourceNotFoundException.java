package be.tsapasmi33.digitalcityairport.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Class<?> className, Long id) {
        super(className.getSimpleName() + " with id[" + id + "] does not exist!");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
