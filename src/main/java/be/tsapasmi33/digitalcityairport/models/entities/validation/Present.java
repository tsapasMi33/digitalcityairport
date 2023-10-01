package be.tsapasmi33.digitalcityairport.models.entities.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PresentValidator.class)
public @interface Present {
    String message() default "Date cannot be in the past or present";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
