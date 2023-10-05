package be.tsapasmi33.digitalcityairport.repositories;

import be.tsapasmi33.digitalcityairport.models.entities.Flight;
import be.tsapasmi33.digitalcityairport.models.entities.Passenger;
import be.tsapasmi33.digitalcityairport.models.entities.enums.FidelityStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    @Transactional
    @Modifying
    @Query("""
            UPDATE Passenger p
            SET p.fidelity = :fidelity
            WHERE p.id = :id
            """)
    void updateFidelity(long id, FidelityStatus fidelity);

    @Query("""
            SELECT f FROM Flight f
            JOIN f.reservations r
            JOIN r.passenger p
            WHERE p.id = :PassengerId
            AND (:includeCancelled IS NULL OR r.cancelled = :includeCancelled)
                """)
    List<Flight> findFlightsByPassenger(Long PassengerId, Boolean includeCancelled);
}
