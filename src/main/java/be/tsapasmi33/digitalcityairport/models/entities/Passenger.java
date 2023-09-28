package be.tsapasmi33.digitalcityairport.models.entities;

import be.tsapasmi33.digitalcityairport.models.entities.enums.FidelityStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Passenger extends Person {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FidelityStatus fidelity;

    @JsonIgnore
    @OneToMany(mappedBy = "passenger")
    private List<Reservation> reservations;


}
