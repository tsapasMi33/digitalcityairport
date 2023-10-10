package be.tsapasmi33.digitalcityairport.models.form.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AirplaneAvailableValidator.class)
public @interface AirplaneAvailable {

    String message() default "Airplane is not available on these dates!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
