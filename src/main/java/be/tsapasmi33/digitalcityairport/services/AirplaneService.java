package be.tsapasmi33.digitalcityairport.services;

import be.tsapasmi33.digitalcityairport.models.entities.Airplane;
import be.tsapasmi33.digitalcityairport.models.entities.Airport;

import java.time.LocalDateTime;

public interface AirplaneService extends CrudService<Airplane, Long> {


    Airplane findBySerialNo(String serialNo);

    void setCurrentAirport(long id, long airportId);

    boolean isAvailable(Airplane airplane, LocalDateTime departure, LocalDateTime arrival, Airport origin);
}
