package be.tsapasmi33.digitalcityairport.models.dto;

import be.tsapasmi33.digitalcityairport.models.entities.Airport;
import be.tsapasmi33.digitalcityairport.models.entities.Flight;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FlightDTO {
    private Long id;

    private String code;

    private LocalDateTime departure;

    private LocalDateTime arrival;

    private boolean cancelled;

    private AirportDTO origin;

    private AirportDTO destination;

    public static FlightDTO toDto(Flight flight) {
        if (flight == null) return null;

        return FlightDTO.builder()
                .id(flight.getId())
                .code(flight.getCode())
                .departure(flight.getDeparture())
                .arrival(flight.getArrival())
                .cancelled(flight.isCancelled())
                .origin(AirportDTO.toDto(flight.getOrigin()))
                .destination(AirportDTO.toDto(flight.getDestination()))
                .build();
    }
}
