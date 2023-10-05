package be.tsapasmi33.digitalcityairport.services.impl;

import be.tsapasmi33.digitalcityairport.exceptions.ResourceNotAvailableException;
import be.tsapasmi33.digitalcityairport.exceptions.ResourceNotFoundException;
import be.tsapasmi33.digitalcityairport.models.entities.Airplane;
import be.tsapasmi33.digitalcityairport.models.entities.AirplaneType;
import be.tsapasmi33.digitalcityairport.models.entities.Flight;
import be.tsapasmi33.digitalcityairport.models.entities.Pilot;
import be.tsapasmi33.digitalcityairport.repositories.FlightRepository;
import be.tsapasmi33.digitalcityairport.services.FlightService;
import be.tsapasmi33.digitalcityairport.services.PilotService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final PilotService pilotService;

    @Override
    public List<Flight> getAll() {
        return flightRepository.findAll();
    }

    @Override
    public Flight getOne(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Flight.class, id));
    }

    @Override
    public void insert(Flight entity) {
        if (entity.getOrigin() == entity.getDestination()) {
            throw new IllegalArgumentException("Origin and Destination airports cannot be the same!");
        }
        if (entity.getDeparture().isBefore(LocalDateTime.now().plusDays(7))) {
            throw new IllegalArgumentException("Flight must be scheduled at least 7 days in advance!");
        }
        if (entity.getDeparture().isAfter(entity.getArrival())) {
            throw new IllegalArgumentException("Departure must be before arrival");
        }

        boolean airplaneUnoccupied = entity.getAirplane().getFlights().stream()
                .allMatch(flight -> entity.getArrival().isBefore(flight.getDeparture()) || entity.getDeparture().isAfter(flight.getArrival()));

        if (!airplaneUnoccupied) {
            throw new ResourceNotAvailableException(Airplane.class, entity.getAirplane().getId(), "on these dates");
        }

        boolean pilotUnoccupied = entity.getPilot().getFlights().stream()
                .allMatch(flight -> entity.getArrival().isBefore(flight.getDeparture()) || entity.getDeparture().isAfter(flight.getArrival()));
        if (!pilotUnoccupied) {
            throw new ResourceNotAvailableException(Pilot.class, entity.getPilot().getId(), "on these dates");
        }

        if (!pilotService.checkLicence(entity.getPilot().getId(), entity.getAirplane().getType())) {
            throw new ResourceNotAvailableException(Pilot.class, entity.getPilot().getId(), "because he has no licence for this airplane type");
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
    public void cancelFlight(long id) {
        if (!flightRepository.existsById(id)) {
            throw new IllegalArgumentException("No flight with this id");
        }
        if (flightRepository.existsByIdAndCancelledIsTrue(id)) {
            throw new IllegalArgumentException("Flight is already cancelled");
        }
        flightRepository.cancelFlight(id);
    }

    @Override
    public boolean isDepartureAfterXDays(long flightId, LocalDateTime limitDate) {
        return flightRepository.isDepartureAfterXDays(flightId, limitDate);
    }

    @Override
    public boolean areSeatsAvailable(Flight flight) {
        AirplaneType airplaneType = flight.getAirplane().getType();
        if (airplaneType.getCapacity() > flight.getReservations().size()) {
            return true;
        }
        throw new ResourceNotAvailableException(Flight.class, flight.getId(), "because all seats are occupied");
    }


}
