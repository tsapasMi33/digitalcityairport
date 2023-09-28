package be.tsapasmi33.digitalcityairport.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Airplane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String serialNo;

    private LocalDate constructionDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "airplane_type_id")
    private AirplaneType type;
}
