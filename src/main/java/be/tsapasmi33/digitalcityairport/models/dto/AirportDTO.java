package be.tsapasmi33.digitalcityairport.models.dto;

import be.tsapasmi33.digitalcityairport.models.entities.Airport;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AirportDTO {

    private Long id;

    private String code;

    private String name;

    private String address;


    public static AirportDTO toDto(Airport airport) {
        if (airport == null) return null;

        return AirportDTO.builder()
                .id(airport.getId())
                .code(airport.getCode())
                .name(airport.getName())
                .address(airport.getAddress())
                .build();
    }
}
