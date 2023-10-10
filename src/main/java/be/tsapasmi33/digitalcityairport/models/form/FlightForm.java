package be.tsapasmi33.digitalcityairport.models.form;

import be.tsapasmi33.digitalcityairport.models.entities.Flight;
import be.tsapasmi33.digitalcityairport.models.form.validation.AirplaneAvailable;
import be.tsapasmi33.digitalcityairport.models.form.validation.PilotAvailable;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@PilotAvailable
@AirplaneAvailable
public class FlightForm {

    @Positive(message = "Pilot ID must be a positive")
    Long pilotId;
    @Positive(message = "Airplane ID must be a positive")
    Long airplaneId;
    @Positive(message = "Origin airport ID must be a positive")
    Long originAirportId;
    @Positive(message = "Destination airport ID must be a positive")
    Long destinationAirportId;
    @Pattern(regexp = "^[A-Z]{2}[0-9]{4}$", message = "Flight code must be of type AB1234")
    private String code;
    @Future(message = "Departure must be in the future")
    @NotNull
    private LocalDateTime departure;
    @Future(message = "Arrival must be in the future")
    @NotNull
    private LocalDateTime arrival;
    @PositiveOrZero(message = "Price cannot be negative")
    private double price;

    public Flight toEntity() {
        Flight flight = new Flight();
        flight.setCode(code);
        flight.setDeparture(departure);
        flight.setArrival(arrival);
        flight.setPrice(price);

        return flight;
    }
}
