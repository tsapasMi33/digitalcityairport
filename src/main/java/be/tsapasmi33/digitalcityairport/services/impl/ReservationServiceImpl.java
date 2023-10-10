package be.tsapasmi33.digitalcityairport.services.impl;

import be.tsapasmi33.digitalcityairport.exceptions.CancelledEventModificationException;
import be.tsapasmi33.digitalcityairport.exceptions.ResourceNotFoundException;
import be.tsapasmi33.digitalcityairport.models.entities.Flight;
import be.tsapasmi33.digitalcityairport.models.entities.Passenger;
import be.tsapasmi33.digitalcityairport.models.entities.Reservation;
import be.tsapasmi33.digitalcityairport.repositories.ReservationRepository;
import be.tsapasmi33.digitalcityairport.services.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    @Override
    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation getOne(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Reservation.class, id));
    }

    @Override
    public void insert(Reservation entity) {
        reservationRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public Reservation update(Long id, Reservation entity) {
        Reservation old = getOne(id);

        entity.setId(old.getId());
        entity.setPassenger(old.getPassenger());
        entity.setPrice(old.getPrice());
        entity.setFlight(old.getFlight());

        return reservationRepository.save(entity);
    }

    @Override
    public List<Reservation> findAllByCriteria(LocalDate reservationDate, Boolean cancelled, Flight flight, Passenger passenger) {
        return reservationRepository.findByReservationDateAndCancelledAndFlightAndPassenger(reservationDate, cancelled, flight, passenger);
    }

    @Override
    public void cancelReservation(long id) {
        Reservation reservation = getOne(id);
        if (reservation.isCancelled()) {
            throw new CancelledEventModificationException(Reservation.class, id);
        }

        reservationRepository.cancelReservation(id);
    }
}
