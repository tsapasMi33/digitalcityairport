package be.tsapasmi33.digitalcityairport.models.form.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PilotAvailableValidator.class)
public @interface PilotAvailable {
    String message() default "Pilot is not available on these dates!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
