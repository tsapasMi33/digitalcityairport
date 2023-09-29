package be.tsapasmi33.digitalcityairport.repositories;

import be.tsapasmi33.digitalcityairport.models.entities.Flight;
import be.tsapasmi33.digitalcityairport.models.entities.Passenger;
import be.tsapasmi33.digitalcityairport.models.entities.Reservation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByReservationDateAndCancelledAndFlightAndPassenger(LocalDate reservationDate, Boolean cancelled, Flight flight, Passenger passenger);

    @Transactional
    @Modifying
    @Query("""
            UPDATE Reservation r
            SET r.cancelled = true
            """)
    void cancelReservation(long id);
}
