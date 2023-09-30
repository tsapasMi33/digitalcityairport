package be.tsapasmi33.digitalcityairport.models.dto;

import be.tsapasmi33.digitalcityairport.models.entities.Passenger;
import be.tsapasmi33.digitalcityairport.models.entities.enums.FidelityStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PassengerDTO {
    private Long id;

    private String fullName;

    private FidelityStatus fidelity;

    public static PassengerDTO toDTO(Passenger passenger) {
        if (passenger == null) return null;

        return PassengerDTO.builder()
                .id(passenger.getId())
                .fullName(passenger.getFirstname() + " " + passenger.getLastname())
                .fidelity(passenger.getFidelity())
                .build();
    }
}
