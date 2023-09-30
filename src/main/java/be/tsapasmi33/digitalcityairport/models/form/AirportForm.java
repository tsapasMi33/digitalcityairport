package be.tsapasmi33.digitalcityairport.models.form;

import be.tsapasmi33.digitalcityairport.models.entities.Airport;
import lombok.Data;

@Data
public class AirportForm {
    private String code;
    private String name;
    private String address;

    public Airport toEntity() {
        Airport airport = new Airport();
        airport.setCode(code);
        airport.setName(name);
        airport.setAddress(address);

        return airport;
    }
}
