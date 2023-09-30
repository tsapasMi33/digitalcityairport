package be.tsapasmi33.digitalcityairport.repositories;

import be.tsapasmi33.digitalcityairport.models.entities.Flight;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    boolean existsByIdAndCancelledIsTrue(long id);

    @Query("""
            SELECT 
                CASE 
                    WHEN COUNT(f) > 0 
                    THEN true 
                    ELSE false 
                END
            FROM Flight f
            WHERE f.id = :id
            AND f.departure > :limitDate
            """)
    boolean isDepartureAfterXDays(long id, LocalDateTime limitDate);

    @Transactional
    @Modifying
    @Query("""
            UPDATE Flight f
            SET f.cancelled = true
            """)
    void cancelFlight(long id);
}
