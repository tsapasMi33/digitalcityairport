package be.tsapasmi33.digitalcityairport.models.form;

import be.tsapasmi33.digitalcityairport.models.entities.Reservation;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationForm {
    @PositiveOrZero(message = "Price cannot be negative!")
    double price;

    @Positive(message = "Flight ID cannot be negative!")
    long flightId;

    @Positive(message = "Passenger ID cannot be negative!")
    long passengerId;

    public Reservation toEntity() {
        Reservation reservation = new Reservation();
        reservation.setReservationDate(LocalDate.now());
        return reservation;
    }
}
