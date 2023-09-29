package be.tsapasmi33.digitalcityairport.models.dto;

import be.tsapasmi33.digitalcityairport.models.entities.Airplane;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class AirplaneDTO {
    private Long id;

    private String serialNo;

    private LocalDate constructionDate;

    private AirplaneTypeDTO type;

    public static AirplaneDTO toDto(Airplane airplane) {
        if (airplane == null) {
            return null;
        }

        return AirplaneDTO.builder()
                .id(airplane.getId())
                .serialNo(airplane.getSerialNo())
                .constructionDate(airplane.getConstructionDate())
                .type(AirplaneTypeDTO.toDto(airplane.getType()))
                .build();

    }
}
