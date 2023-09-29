package be.tsapasmi33.digitalcityairport.models.dto;

import be.tsapasmi33.digitalcityairport.models.entities.AirplaneType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Builder
public class AirplaneTypeDTO {
    private Long id;
    private String make;

    private String model;

    private String capacity;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AirplaneTypeDTO that = (AirplaneTypeDTO) o;

        if (!Objects.equals(make, that.make)) return false;
        if (!Objects.equals(model, that.model)) return false;
        return Objects.equals(capacity, that.capacity);
    }

    @Override
    public int hashCode() {
        int result = make != null ? make.hashCode() : 0;
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (capacity != null ? capacity.hashCode() : 0);
        return result;
    }
}
