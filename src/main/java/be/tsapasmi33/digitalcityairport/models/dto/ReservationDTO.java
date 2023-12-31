package be.tsapasmi33.digitalcityairport.models.dto;

import be.tsapasmi33.digitalcityairport.models.entities.Reservation;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ReservationDTO {
    private Long ReservationId;

    private double price;

    private LocalDate reservationDate;

    private boolean cancelled;

    private PassengerDTO passenger;

    public static ReservationDTO toDto(Reservation reservation) {
        if (reservation == null) return null;

        return ReservationDTO.builder()
                .ReservationId(reservation.getId())
                .price(reservation.getPrice())
                .reservationDate(reservation.getReservationDate())
                .cancelled(reservation.isCancelled())
                .passenger(PassengerDTO.toDTO(reservation.getPassenger()))
                .build();
    }
}
