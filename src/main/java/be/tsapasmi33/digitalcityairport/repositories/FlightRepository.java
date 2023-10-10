package be.tsapasmi33.digitalcityairport.repositories;

import be.tsapasmi33.digitalcityairport.models.entities.Airport;
import be.tsapasmi33.digitalcityairport.models.entities.Flight;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    @Query("""
        SELECT COUNT(f) = 0
        FROM Flight f
        JOIN f.pilot p
        WHERE p.id = :pilotId
        AND
            (f.departure BETWEEN :departure AND :arrival
            OR f.arrival BETWEEN :departure AND :arrival
            OR (f.departure <= :departure AND f.arrival >= : arrival)
            )
        """)
    boolean isPilotAvailableOnDates(long pilotId, LocalDateTime departure, LocalDateTime arrival);

    @Query("""
        SELECT COUNT(f) = 0
        FROM Flight f
        JOIN f.airplane a
        WHERE a.id = :airplaneId
        AND
            (f.departure BETWEEN :departure AND :arrival
            OR f.arrival BETWEEN :departure AND :arrival
            OR (f.departure <= :departure AND f.arrival >= : arrival)
            )
        """)
    boolean isAirplaneAvailableOnDates(long airplaneId, LocalDateTime departure, LocalDateTime arrival);

    @Query("""
    SELECT f
    FROM Flight f
    WHERE (:fromAirport IS NULL OR f.origin = :fromAirport)
    AND (:toAirport IS NULL OR f.destination = :toAirport)
    AND (CAST(:date AS DATE) IS NULL OR CAST(f.departure AS DATE) = CAST(:date AS DATE))
    AND (:min IS NULL OR f.price >= : min)
    AND (:max IS NULL OR f.price <= : max)
    
""")
    List<Flight> findAllByCriteria(Airport fromAirport, Airport toAirport, LocalDate date, Double min, Double max);



    @Query("""
       SELECT f
       FROM Flight f
       JOIN f.airplane a
       WHERE CAST(a.constructionDate AS DATE) > CAST(:tenYearsBefore AS DATE)
""")
    List<Flight> findFlightsWithNewAirplanes(LocalDate tenYearsBefore);

}
