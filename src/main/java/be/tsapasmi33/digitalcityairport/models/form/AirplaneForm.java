package be.tsapasmi33.digitalcityairport.models.form;

import be.tsapasmi33.digitalcityairport.models.entities.Airplane;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AirplaneForm {

    @NotBlank(message = "Serial Number cannot be blank")
    @NotNull
    private String serialNo;

    @Past(message = "Construction date must be in the past")
    private LocalDate constructionDate;

    @Positive(message = "Airplane Type id must be a positive")
    private long typeId;

    @Positive(message = "Airport id must be a positive")
    private long airportId;

    public Airplane toEntity() {
        Airplane airplane = new Airplane();
        airplane.setSerialNo(serialNo);
        airplane.setConstructionDate(constructionDate);

        return airplane;
    }
}
