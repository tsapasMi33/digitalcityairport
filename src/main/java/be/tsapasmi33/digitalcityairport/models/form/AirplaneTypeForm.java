package be.tsapasmi33.digitalcityairport.models.form;

import be.tsapasmi33.digitalcityairport.models.entities.AirplaneType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AirplaneTypeForm {

    @NotBlank(message = "Make cannot be blank")
    private String make;

    @NotBlank(message = "Model cannot be blank")
    private String model;

    @Positive
    private int capacity;

    public AirplaneType toEntity() {
        AirplaneType airplaneType = new AirplaneType();
        airplaneType.setMake(make);
        airplaneType.setModel(model);
        airplaneType.setCapacity(capacity);
        return airplaneType;
    }
}
