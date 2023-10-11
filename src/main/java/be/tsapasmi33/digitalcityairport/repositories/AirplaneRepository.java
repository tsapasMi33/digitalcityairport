package be.tsapasmi33.digitalcityairport.repositories;

import be.tsapasmi33.digitalcityairport.models.entities.Airplane;
import be.tsapasmi33.digitalcityairport.models.entities.Airport;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AirplaneRepository extends JpaRepository<Airplane, Long> {
    @Transactional
    @Modifying
    @Query("""
            UPDATE Airplane a
            SET a.currentAirport = :airport
            WHERE a.id = :airplaneId
            """)
    void setCurrentAirport(Long airplaneId, Airport airport);

    @Query(nativeQuery = true,
            value = """
                SELECT * FROM airplane a
                WHERE a.id IN (
                    SELECT airplane_id FROM
                        (SELECT f.airplane_id, COUNT(f.id)
                            FROM flight f
                            WHERE CAST(f.departure AS DATE) <= now()
                            GROUP BY f.airplane_id
                        ) a
                    WHERE a.count >= 10
)
                    """)
    List<Airplane> findAllExperiencedAirplanes();
}
