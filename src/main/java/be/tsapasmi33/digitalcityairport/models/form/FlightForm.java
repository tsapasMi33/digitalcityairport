package be.tsapasmi33.digitalcityairport.models.form;

import be.tsapasmi33.digitalcityairport.models.entities.Flight;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightForm {

    @Positive(message = "Pilot id must be a positive")
    Long pilotId;
    @Positive(message = "Airplane id must be a positive")
    Long airplaneId;
    @Positive(message = "Origin airport id must be a positive")
    Long originAirportId;
    @Positive(message = "Destination airport id must be a positive")
    Long destinationAirportId;
    @Pattern(regexp = "^[A-Z]{2}[0-9]{4}$", message = "Flight code myst be of type AB1234")
    private String code;
    @Future(message = "Departure must be in the future")
    private LocalDateTime departure;
    @Future(message = "Arrival must be in the future")
    private LocalDateTime arrival;
    @Min(value = 0, message = "Price cannot be negative")
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
