package be.tsapasmi33.digitalcityairport.models.dto;

import be.tsapasmi33.digitalcityairport.models.entities.FidelityStatus;
import be.tsapasmi33.digitalcityairport.models.entities.Person;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonDTO {

    private long id;
    private String name;
    private FidelityStatus fidelity;

    public static PersonDTO toDto(Person person){
        if( person == null )
            return null;

        return PersonDTO.builder()
                .id(person.getId())
                .name(person.getFirstname() + ' ' + person.getLastname())
                .fidelity(person.getFidelity())
                .build();
    }

}