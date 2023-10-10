package be.tsapasmi33.digitalcityairport.services.impl;

import be.tsapasmi33.digitalcityairport.exceptions.CancelledEventModificationException;
import be.tsapasmi33.digitalcityairport.exceptions.ConstraintNotRespectedException;
import be.tsapasmi33.digitalcityairport.exceptions.ResourceNotAvailableException;
import be.tsapasmi33.digitalcityairport.exceptions.ResourceNotFoundException;
import be.tsapasmi33.digitalcityairport.models.entities.*;
import be.tsapasmi33.digitalcityairport.repositories.FlightRepository;
import be.tsapasmi33.digitalcityairport.services.FlightService;
import be.tsapasmi33.digitalcityairport.services.PilotService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        List<RuntimeException> exceptions = new ArrayList<>();
        if (entity.getOrigin() == entity.getDestination()) {
            throw new ConstraintNotRespectedException("Origin and Destination airports cannot be the same!");
        }
        if (entity.getDeparture().isBefore(LocalDateTime.now().plusDays(7))) {
            throw new ConstraintNotRespectedException("Flight must be scheduled at least 7 days in advance!");
        }
        if (entity.getDeparture().isAfter(entity.getArrival())) {
            throw new ConstraintNotRespectedException("Departure must be before arrival");
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
            throw new CancelledEventModificationException(Flight.class, id);
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
            throw new ResourceNotFoundException(Flight.class, id);
        }
        if (flightRepository.existsByIdAndCancelledIsTrue(id)) {
            throw new CancelledEventModificationException(Flight.class, id);
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

    @Override
    public List<Flight> findAllByCriteria(Airport fromAirport, Airport toAirport, LocalDate date, Double min, Double max) {
        return flightRepository.findAllByCriteria(fromAirport,toAirport,date,min,max);
    }

    @Override
    public List<Flight> findFlightsWithNewAirplanes() {
        return flightRepository.findFlightsWithNewAirplanes(LocalDate.now().minusYears(10));
    }


}
