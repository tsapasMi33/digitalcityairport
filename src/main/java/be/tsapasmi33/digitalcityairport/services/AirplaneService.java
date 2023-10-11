package be.tsapasmi33.digitalcityairport.services;

import be.tsapasmi33.digitalcityairport.models.entities.Airplane;
import be.tsapasmi33.digitalcityairport.models.entities.Airport;

import java.util.List;

public interface AirplaneService extends CrudService<Airplane, Long> {


    Airplane findBySerialNo(String serialNo);

    void setCurrentAirport(long id, Airport airport);

    List<Airplane> findAllExperiencedAirplanes();
}
