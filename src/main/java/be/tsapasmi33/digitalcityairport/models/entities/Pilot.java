package be.tsapasmi33.digitalcityairport.models.entities;

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
public class Pilot extends Person {

    @JsonIgnore
    @OneToMany(mappedBy = "pilot")
    private List<Flight> flights;

    @ManyToMany
    @JoinTable(
            name = "pilot_licenses",
            joinColumns = @JoinColumn(name = "pilot_id"),
            inverseJoinColumns = @JoinColumn(name = "airplane_type_id")
    )
    private List<AirplaneType> licences;
}
