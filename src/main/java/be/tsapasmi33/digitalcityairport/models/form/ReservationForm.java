package be.tsapasmi33.digitalcityairport.models.form;

import be.tsapasmi33.digitalcityairport.models.entities.Reservation;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationForm {
    private LocalDate reservationDate;

    public Reservation toEntity() {
        Reservation reservation = new Reservation();
        reservation.setReservationDate(reservationDate);
        return reservation;
    }
}
