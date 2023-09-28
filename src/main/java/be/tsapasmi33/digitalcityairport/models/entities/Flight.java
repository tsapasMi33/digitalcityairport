package be.tsapasmi33.digitalcityairport.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private LocalDateTime departure;

    private LocalDateTime arrival;

    private double price;

    private boolean cancelled;

    @ManyToOne
    @JoinColumn(name = "pilot_id")
    private Pilot pilot;

    @ManyToOne
    @JoinColumn(name = "airplane_id")
    private Airplane airplane;

    @ManyToOne
    @JoinColumn(name= "origin_airport_id")
    private Airport origin;

    @ManyToOne
    @JoinColumn(name= "destination_airport_id")
    private Airport destination;

    @JsonIgnore
    @OneToMany(mappedBy = "flight")
    private List<Reservation> reservations;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flight flight = (Flight) o;

        if (!Objects.equals(code, flight.code)) return false;
        if (!Objects.equals(departure, flight.departure)) return false;
        if (!Objects.equals(arrival, flight.arrival)) return false;
        if (!Objects.equals(origin, flight.origin)) return false;
        return Objects.equals(destination, flight.destination);
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (departure != null ? departure.hashCode() : 0);
        result = 31 * result + (arrival != null ? arrival.hashCode() : 0);
        result = 31 * result + (origin != null ? origin.hashCode() : 0);
        result = 31 * result + (destination != null ? destination.hashCode() : 0);
        return result;
    }
}
