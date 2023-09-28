package be.tsapasmi33.digitalcityairport.models.form;

import be.tsapasmi33.digitalcityairport.models.entities.AirplaneType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AirplaneTypeForm {
    private String make;

    private String model;

    private String capacity;

    public AirplaneType toEntity() {
        AirplaneType airplaneType = new AirplaneType();
        airplaneType.setMake(make);
        airplaneType.setModel(model);
        airplaneType.setCapacity(capacity);
        return airplaneType;
    }
}
