package be.tsapasmi33.digitalcityairport.services;

import be.tsapasmi33.digitalcityairport.models.entities.Airplane;

public interface AirplaneService extends CrudService<Airplane, Long> {
    Airplane findBySerialNo(String serialNo);

}
