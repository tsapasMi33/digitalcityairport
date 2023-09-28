package be.tsapasmi33.digitalcityairport.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long id;

    @Column(name = "person_firstname", nullable = false)
    private String firstname;

    @Column(name = "person_lastname", nullable = false)
    private String lastname;

    private String idNo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (!Objects.equals(firstname, person.firstname)) return false;
        if (!Objects.equals(lastname, person.lastname)) return false;
        return Objects.equals(idNo, person.idNo);
    }

    @Override
    public int hashCode() {
        int result = firstname != null ? firstname.hashCode() : 0;
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (idNo != null ? idNo.hashCode() : 0);
        return result;
    }
}
