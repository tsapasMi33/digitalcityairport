package be.tsapasmi33.digitalcityairport.repositories;

import be.tsapasmi33.digitalcityairport.models.entities.Airplane;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AirplaneRepository extends JpaRepository<Airplane, Long> {
    @Transactional
    @Modifying
    @Query("""
            UPDATE Airplane a
            SET a.currentAirport = :airportId
            WHERE a.id = :airplaneId
            """)
    void setCurrentAirport(Long airplaneId, Long airportId);
}
