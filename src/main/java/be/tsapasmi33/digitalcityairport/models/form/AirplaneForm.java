package be.tsapasmi33.digitalcityairport.models.form;

import be.tsapasmi33.digitalcityairport.models.entities.Airplane;
import be.tsapasmi33.digitalcityairport.models.entities.AirplaneType;

import java.time.LocalDate;

public class AirplaneForm {
    private String serialNo;
    private LocalDate constructionDate;
    private AirplaneType type;

    public Airplane toEntity() {
        Airplane airplane = new Airplane();
        airplane.setSerialNo(serialNo);
        airplane.setConstructionDate(constructionDate);
        airplane.setType(type);

        return airplane;
    }
}
