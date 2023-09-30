package be.tsapasmi33.digitalcityairport.services.impl;

import be.tsapasmi33.digitalcityairport.exceptions.ReservationNotFoundException;
import be.tsapasmi33.digitalcityairport.models.entities.Flight;
import be.tsapasmi33.digitalcityairport.models.entities.Passenger;
import be.tsapasmi33.digitalcityairport.models.entities.Reservation;
import be.tsapasmi33.digitalcityairport.repositories.ReservationRepository;
import be.tsapasmi33.digitalcityairport.services.FlightService;
import be.tsapasmi33.digitalcityairport.services.PassengerService;
import be.tsapasmi33.digitalcityairport.services.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final PassengerService passengerService;
    private final FlightService flightService;

    @Override
    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation getOne(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));
    }

    @Override
    public void insert(Reservation entity) {
        reservationRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        reservationRepository.deleteById(id);
    }

    @Override //TODO check if useful
    public Reservation update(Long id, Reservation entity) {
        Reservation old = getOne(id);

        entity.setId(old.getId());
        entity.setPassenger(old.getPassenger());
        entity.setPrice(old.getPrice());
        entity.setFlight(old.getFlight());

        return reservationRepository.save(entity);
    }

    @Override
    public List<Reservation> findAllByCriteria(LocalDate reservationDate, Boolean cancelled, Long flightId, Long passengerId) {
        Flight flight = null;
        Passenger passenger = null;

        if (flightId != null) {
            flight = flightService.getOne(flightId);
        }
        if (passengerId != null) {
            passenger = passengerService.getOne(passengerId);
        }

        return reservationRepository.findByReservationDateAndCancelledAndFlightAndPassenger(reservationDate, cancelled, flight, passenger);
    }

    @Override
    public void cancelReservation(long id) {
        Reservation reservation = getOne(id);
        if (reservation.isCancelled()) {
            throw new IllegalArgumentException("Reservation already cancelled!");
        }
        if (!flightService.isDepartureAfterXDays(reservation.getFlight().getId(), LocalDateTime.now().plusDays(3))) {
            throw new IllegalArgumentException("Flight is less than three days ahead!");
        }

        reservationRepository.cancelReservation(id);
    }
}
