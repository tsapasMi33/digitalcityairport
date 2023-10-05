package be.tsapasmi33.digitalcityairport.models.dto;

import be.tsapasmi33.digitalcityairport.models.entities.Reservation;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ReservationDTORich {
    private Long id;

    private double price;

    private LocalDate reservationDate;

    private boolean cancelled;

    private FlightDTO flight;

    private PassengerDTO passenger;

    public static ReservationDTORich toDto(Reservation reservation) {
        if (reservation == null) return null;

        return ReservationDTORich.builder()
                .id(reservation.getId())
                .price(reservation.getPrice())
                .reservationDate(reservation.getReservationDate())
                .cancelled(reservation.isCancelled())
                .flight(FlightDTO.toDto(reservation.getFlight()))
                .passenger(PassengerDTO.toDTO(reservation.getPassenger()))
                .build();
    }
}
