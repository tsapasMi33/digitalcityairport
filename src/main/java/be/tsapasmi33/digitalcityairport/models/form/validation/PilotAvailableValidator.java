package be.tsapasmi33.digitalcityairport.models.form.validation;

import be.tsapasmi33.digitalcityairport.models.form.FlightForm;
import be.tsapasmi33.digitalcityairport.repositories.FlightRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PilotAvailableValidator implements ConstraintValidator<PilotAvailable, FlightForm> {

    FlightRepository flightRepository;

    @Override
    public boolean isValid(FlightForm flightForm, ConstraintValidatorContext constraintValidatorContext) {
        return flightRepository.isPilotAvailableOnDates(flightForm.getPilotId(),flightForm.getDeparture(),flightForm.getArrival());
    }
}
