package be.tsapasmi33.digitalcityairport.models.dto;

import be.tsapasmi33.digitalcityairport.models.entities.Airplane;
import be.tsapasmi33.digitalcityairport.models.entities.AirplaneType;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class AirplaneDTO {
    private String serialNo;

    private LocalDate constructionDate;

    private AirplaneTypeDTO type;
    public static AirplaneDTO toDto(Airplane airplane) {
        if (airplane == null) {
            return null;
        }

        return AirplaneDTO.builder()
                .serialNo(airplane.getSerialNo())
                .constructionDate(airplane.getConstructionDate())
                .type(AirplaneTypeDTO.toDto(airplane.getType()))
                .build();

    }
}
