package be.tsapasmi33.digitalcityairport.repositories;

import be.tsapasmi33.digitalcityairport.models.entities.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirportRepository extends JpaRepository<Airport, Long> {
    Optional<Airport> findByCode(String code);
}
