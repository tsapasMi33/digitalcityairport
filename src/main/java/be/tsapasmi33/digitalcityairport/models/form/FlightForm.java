package be.tsapasmi33.digitalcityairport.models.form;

import be.tsapasmi33.digitalcityairport.models.entities.Flight;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightForm {
    private String code;

    private LocalDateTime departure;

    private LocalDateTime arrival;

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
