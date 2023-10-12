package be.tsapasmi33.digitalcityairport;

import be.tsapasmi33.digitalcityairport.models.entities.*;
import be.tsapasmi33.digitalcityairport.models.entities.enums.FidelityStatus;
import be.tsapasmi33.digitalcityairport.repositories.*;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@AllArgsConstructor
@Component
public class DataInitializer {

    RoleRepository roleRepository;
    AuthorityRepository authorityRepository;

    @PostConstruct
    public void init() {
        Role admin = new Role();
        admin.setName("ADMIN");
        roleRepository.save(admin);

        Role passenger = new Role();
        passenger.setName("PASSENGER");
        roleRepository.save(passenger);

        Role pilot = new Role();
        pilot.setName("PILOT");
        roleRepository.save(pilot);

        Authority flightModifier = new Authority();
        flightModifier.setAuthority("FLIGHT_MODIFIER");

    }
}
