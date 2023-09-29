package be.tsapasmi33.digitalcityairport.services;

import be.tsapasmi33.digitalcityairport.models.entities.Airport;

public interface AirportService extends CrudService<Airport, Long> {
    Airport findByCode(String code);
}
