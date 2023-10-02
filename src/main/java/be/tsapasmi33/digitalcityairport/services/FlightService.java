package be.tsapasmi33.digitalcityairport.services;

import be.tsapasmi33.digitalcityairport.models.entities.Flight;

import java.time.LocalDateTime;

public interface FlightService extends CrudService<Flight, Long> {
    boolean isDepartureAfterXDays(long flightId, LocalDateTime limitDate);

    void cancelFlight(long id);

    boolean areSeatsAvailable(Flight flight);
}
