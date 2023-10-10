package be.tsapasmi33.digitalcityairport.exceptions;

public class ConstraintNotRespectedException extends RuntimeException{
    public ConstraintNotRespectedException(String message) {
        super(message);
    }
}
