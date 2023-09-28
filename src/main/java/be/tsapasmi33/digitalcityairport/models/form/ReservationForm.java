package be.tsapasmi33.digitalcityairport.models.form;

import be.tsapasmi33.digitalcityairport.models.entities.Reservation;

import java.time.LocalDate;

public class ReservationForm {
    private LocalDate reservationDate;

    private Reservation toEntity() {
        Reservation reservation = new Reservation();
        reservation.setReservationDate(reservationDate);
        return reservation;
    }
}
