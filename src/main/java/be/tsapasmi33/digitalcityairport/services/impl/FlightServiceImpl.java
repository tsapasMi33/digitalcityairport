package be.tsapasmi33.digitalcityairport.services.impl;

import be.tsapasmi33.digitalcityairport.exceptions.FlightNotFoundException;
import be.tsapasmi33.digitalcityairport.models.entities.Flight;
import be.tsapasmi33.digitalcityairport.repositories.FlightRepository;
import be.tsapasmi33.digitalcityairport.services.FlightService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    @Override
    public List<Flight> getAll() {
        return flightRepository.findAll();
    }

    @Override
    public Flight getOne(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new FlightNotFoundException(id));
    }

    @Override
    public void insert(Flight entity) {
        if (entity.getOrigin() == entity.getDestination()) {
            throw new IllegalArgumentException("Origin and Destination airports cannot be the same!");
        }
        if (entity.getDeparture().isBefore(LocalDateTime.now().plusDays(7))) {
            throw new IllegalArgumentException("Flight must be scheduled at least 7 days in advance!");
        }
        flightRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        flightRepository.deleteById(id);
    }

    @Override
    public Flight update(Long id, Flight entity) {
        if (flightRepository.existsByIdAndCancelledIsTrue(id)) {
            throw new IllegalArgumentException("Flight is cancelled");
        }

        Flight old = getOne(id);

        entity.setId(old.getId());
        entity.setOrigin(old.getOrigin());
        entity.setDestination(old.getDestination());

        return flightRepository.save(entity);
    }

    @Override
    public boolean isDepartureAfterXDays(long flightId, LocalDateTime limitDate) {
        return flightRepository.isDepartureAfterXDays(flightId, limitDate);
    }

    @Override
    public void cancelFlight(long id) {
        if (!flightRepository.existsById(id)) {
            throw new IllegalArgumentException("No flight with this id");
        }
        if (flightRepository.existsByIdAndCancelledIsTrue(id)) {
            throw new IllegalArgumentException("Flight is already cancelled");
        }
        flightRepository.cancelFlight(id);
    }


}
