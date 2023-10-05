package be.tsapasmi33.digitalcityairport.models.form;

import be.tsapasmi33.digitalcityairport.models.entities.Reservation;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationForm {
    double price;
    long flightId;
    long passengerId;

    public Reservation toEntity() {
        Reservation reservation = new Reservation();
        reservation.setReservationDate(LocalDate.now());
        return reservation;
    }
}
