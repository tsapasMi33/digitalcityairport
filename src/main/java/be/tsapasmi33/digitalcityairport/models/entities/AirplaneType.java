package be.tsapasmi33.digitalcityairport.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AirplaneType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String make;

    private String model;

    private String capacity;

    @JsonIgnore
    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL)
    private List<Airplane> airplanes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AirplaneType that = (AirplaneType) o;

        if (!Objects.equals(make, that.make)) return false;
        if (!Objects.equals(model, that.model)) return false;
        return Objects.equals(capacity, that.capacity);
    }

    @Override
    public int hashCode() {
        int result = make != null ? make.hashCode() : 0;
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (capacity != null ? capacity.hashCode() : 0);
        return result;
    }
}
