package be.tsapasmi33.digitalcityairport.models.dto;

import be.tsapasmi33.digitalcityairport.models.entities.AirplaneType;
import be.tsapasmi33.digitalcityairport.models.entities.Flight;
import be.tsapasmi33.digitalcityairport.models.entities.Pilot;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PilotDTO {

    private Long id;

    private String fullName;

    private List<FlightDTO> flights;

    private List<AirplaneTypeDTO> licences;

    public static PilotDTO toDto(Pilot pilot) {
        if (pilot == null) return null;

        return PilotDTO.builder()
                .id(pilot.getId())
                .fullName(pilot.getFirstname() + " " + pilot.getLastname())
                .flights(pilot.getFlights().stream()
                        .map(FlightDTO::toDto)
                        .toList())
                .licences(pilot.getLicences().stream()
                        .map(AirplaneTypeDTO::toDto)
                        .toList())
                .build();
    }
}
