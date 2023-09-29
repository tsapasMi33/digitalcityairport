package be.tsapasmi33.digitalcityairport.services;

import be.tsapasmi33.digitalcityairport.models.entities.Flight;

public interface FlightService extends CrudService<Flight, Long> {
    void cancelFlight(long id);
}
