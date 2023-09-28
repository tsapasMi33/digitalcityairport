package be.tsapasmi33.digitalcityairport.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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

    @JsonIgnore
    @OneToMany(mappedBy = "airplane")
    private List<Flight> flights;

    @ManyToOne(optional = false)
    @JoinColumn(name = "airplane_type_id")
    private AirplaneType type;

    @ManyToOne
    @JoinColumn(name= "current_airport_id")
    private Airport currentAirport;
}
