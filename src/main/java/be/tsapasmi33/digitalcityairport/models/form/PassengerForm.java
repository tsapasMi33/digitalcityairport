package be.tsapasmi33.digitalcityairport.models.form;

import be.tsapasmi33.digitalcityairport.models.entities.Passenger;

public class PassengerForm {
    private String firstname;
    private String lastname;
    private String idNo;


    public Passenger toEntity() {
        Passenger passenger = new Passenger();
        passenger.setFirstname(firstname);
        passenger.setLastname(lastname);
        passenger.setIdNo(idNo);

        return passenger;
    }
}
