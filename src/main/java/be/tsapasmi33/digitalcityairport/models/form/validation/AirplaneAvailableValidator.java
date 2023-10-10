package be.tsapasmi33.digitalcityairport.models.form.validation;

import be.tsapasmi33.digitalcityairport.models.form.FlightForm;
import be.tsapasmi33.digitalcityairport.repositories.FlightRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AirplaneAvailableValidator implements ConstraintValidator<AirplaneAvailable, FlightForm> {

    FlightRepository flightRepository;

    @Override
    public boolean isValid(FlightForm flightForm, ConstraintValidatorContext constraintValidatorContext) {
        return flightRepository.isAirplaneAvailableOnDates(flightForm.getAirplaneId(), flightForm.getDeparture(),flightForm.getArrival());
    }
}
