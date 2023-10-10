package be.tsapasmi33.digitalcityairport.services;

import be.tsapasmi33.digitalcityairport.models.entities.Airport;
import be.tsapasmi33.digitalcityairport.models.entities.Flight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface FlightService extends CrudService<Flight, Long> {
    boolean isDepartureAfterXDays(long flightId, LocalDateTime limitDate);

    void cancelFlight(long id);

    boolean areSeatsAvailable(Flight flight);

    List<Flight> findAllByCriteria(Airport fromAirport, Airport toAirport, LocalDate date, Double min, Double max);

    List<Flight> findFlightsWithNewAirplanes();
}
