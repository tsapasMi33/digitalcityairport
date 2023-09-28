package be.tsapasmi33.digitalcityairport.models.dto;

import be.tsapasmi33.digitalcityairport.models.entities.Airplane;
import be.tsapasmi33.digitalcityairport.models.entities.Airport;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AirportDTO {

    private Long id;

    private String name;

    private String address;

    private List<AirplaneDTO> airplanesIn;

    public static AirportDTO toDto(Airport airport) {
        if (airport == null) return null;

        return AirportDTO.builder()
                .id(airport.getId())
                .name(airport.getName())
                .address(airport.getAddress())
                .airplanesIn(airport.getAirplanesIn().stream()
                        .map(AirplaneDTO::toDto)
                        .toList())
                .build();
    }
}
