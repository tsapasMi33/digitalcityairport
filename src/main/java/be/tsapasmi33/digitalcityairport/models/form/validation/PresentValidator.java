package be.tsapasmi33.digitalcityairport.models.form.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class PresentValidator implements ConstraintValidator<Present, LocalDate> {

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        return localDate.isEqual(LocalDate.now());
    }
}
