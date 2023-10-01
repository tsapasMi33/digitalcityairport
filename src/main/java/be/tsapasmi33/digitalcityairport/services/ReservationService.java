package be.tsapasmi33.digitalcityairport.services;

import be.tsapasmi33.digitalcityairport.models.entities.Flight;
import be.tsapasmi33.digitalcityairport.models.entities.Passenger;
import be.tsapasmi33.digitalcityairport.models.entities.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService extends CrudService<Reservation, Long> {
    List<Reservation> findAllByCriteria(LocalDate reservationDate, Boolean cancelled, Flight flight, Passenger passenger);

    void cancelReservation(long id);
}
