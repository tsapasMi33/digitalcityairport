package be.tsapasmi33.digitalcityairport.models.form;

import be.tsapasmi33.digitalcityairport.models.entities.Airport;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AirportForm {
    @Pattern(regexp = "^[A-Z]{3}$", message = "Airport codes consist of three capital letters")
    private String code;

    @NotBlank(message = "Name cannot be blank")
    @NotNull
    private String name;

    @NotBlank(message = "Address cannot be blank")
    @NotNull
    private String address;

    public Airport toEntity() {
        Airport airport = new Airport();
        airport.setCode(code);
        airport.setName(name);
        airport.setAddress(address);

        return airport;
    }
}
