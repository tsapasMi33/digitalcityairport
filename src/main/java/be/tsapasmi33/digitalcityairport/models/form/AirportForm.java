package be.tsapasmi33.digitalcityairport.models.form;

import be.tsapasmi33.digitalcityairport.models.entities.Airport;

public class AirportForm {
    private String name;
    private String address;

    public Airport toEntity() {
        Airport airport = new Airport();
        airport.setName(name);
        airport.setAddress(address);

        return airport;
    }
}
