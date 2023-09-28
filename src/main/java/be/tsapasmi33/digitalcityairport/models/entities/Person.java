package be.tsapasmi33.digitalcityairport.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long id;

    @Column(name = "person_firstname", nullable = false)
    private String firstname;

    @Column(name = "person_lastname", nullable = false)
    private String lastname;

    @Enumerated(EnumType.STRING)
    @Column(name = "person_fidelity", nullable = false)
    private FidelityStatus fidelity;

}
