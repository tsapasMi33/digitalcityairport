package be.tsapasmi33.digitalcityairport.models.dto;

import be.tsapasmi33.digitalcityairport.models.entities.AirplaneType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AirplaneTypeDTO {
    private Long id;
    private String make;

    private String model;

    private int capacity;

    public static AirplaneTypeDTO toDto(AirplaneType airplaneType) {
        if (airplaneType == null) {
            return null;
        }

        return AirplaneTypeDTO.builder()
                .id(airplaneType.getId())
                .make(airplaneType.getMake())
                .model(airplaneType.getModel())
                .capacity(airplaneType.getCapacity())
                .build();
    }

}
