package be.tsapasmi33.digitalcityairport.services;

import be.tsapasmi33.digitalcityairport.models.entities.Flight;
import be.tsapasmi33.digitalcityairport.models.entities.Passenger;
import be.tsapasmi33.digitalcityairport.models.entities.enums.FidelityStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PassengerService extends CrudService<Passenger, Long> {
    void updateFidelity(long id, FidelityStatus fidelity);

    List<Flight> getReservedFlights(long passengerId, Boolean includeCancelled);

    Page<Passenger> getByFidelity(FidelityStatus fidelityStatus, int pageNumber, int countByPage);
}
