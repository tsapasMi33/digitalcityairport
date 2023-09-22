package be.tsapasmi33.digitalcityairport.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;
@Data
public class Person {
    private String id;
    @NonNull
    private String firstname;
    @NonNull
    private String lastname;
    private FidelityStatus status;

    public Person() {
        this.id = UUID.randomUUID().toString();
        this.status = FidelityStatus.NONE;
    }

    enum FidelityStatus {
        NONE,
        BASIC,
        PREMIUM,
        GOLD
    }
}
