package be.tsapasmi33.digitalcityairport.models.form;

import be.tsapasmi33.digitalcityairport.models.entities.Pilot;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PilotForm {
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, message = "First name is too short")
    @NotNull
    private String firstname;

    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 2, message = "First name is too short")
    @NotNull
    private String lastname;

    @NotBlank(message = "Id Number cannot be blank")
    @NotNull
    private String idNo;


    public Pilot toEntity() {
        Pilot pilot = new Pilot();
        pilot.setLastname(lastname);
        pilot.setFirstname(firstname);
        pilot.setIdNo(idNo);

        return pilot;
    }
}
