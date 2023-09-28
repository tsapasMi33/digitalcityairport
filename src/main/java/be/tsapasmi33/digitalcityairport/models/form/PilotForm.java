package be.tsapasmi33.digitalcityairport.models.form;

import be.tsapasmi33.digitalcityairport.models.entities.Pilot;

public class PilotForm {
    private String firstname;
    private String lastname;
    private String idNo;


    public Pilot toEntity() {
        Pilot pilot = new Pilot();
        pilot.setLastname(lastname);
        pilot.setFirstname(firstname);
        pilot.setIdNo(idNo);

        return pilot;
    }
}
