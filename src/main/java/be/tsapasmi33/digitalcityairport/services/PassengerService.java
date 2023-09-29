package be.tsapasmi33.digitalcityairport.services;

import be.tsapasmi33.digitalcityairport.models.entities.Passenger;
import be.tsapasmi33.digitalcityairport.models.entities.enums.FidelityStatus;

public interface PassengerService extends CrudService<Passenger, Long> {
    void updateFidelity(long id, FidelityStatus fidelity);
}
