package be.tsapasmi33.digitalcityairport.services.impl;

import be.tsapasmi33.digitalcityairport.exceptions.CancelledEventModificationException;
import be.tsapasmi33.digitalcityairport.exceptions.ConstraintNotRespectedException;
import be.tsapasmi33.digitalcityairport.exceptions.ResourceNotAvailableException;
import be.tsapasmi33.digitalcityairport.exceptions.ResourceNotFoundException;
import be.tsapasmi33.digitalcityairport.models.entities.AirplaneType;
import be.tsapasmi33.digitalcityairport.models.entities.Airport;
import be.tsapasmi33.digitalcityairport.models.entities.Flight;
import be.tsapasmi33.digitalcityairport.models.entities.Pilot;
import be.tsapasmi33.digitalcityairport.repositories.FlightRepository;
import be.tsapasmi33.digitalcityairport.services.FlightService;
import be.tsapasmi33.digitalcityairport.services.PilotService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
    private final EntityManager entityManager;

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
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Flight> query = criteriaBuilder.createQuery(Flight.class);
        Root<Flight> root = query.from(Flight.class);
        List<Predicate> predicates = new ArrayList<>();

        if (fromAirport != null) {
            predicates.add(criteriaBuilder.equal(root.get("origin"), fromAirport));
        }
        if (toAirport != null) {
            predicates.add(criteriaBuilder.equal(root.get("destination"), toAirport));
        }
        if (toAirport != null) {
            predicates.add(criteriaBuilder.equal(root.get("departure"), date));
        }
        if (toAirport != null) {
            predicates.add(criteriaBuilder.equal(root.get("destination"), toAirport));
        }
        if (min != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), min));
        }
        if (max != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), max));
        }

        query.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Flight> typedQuery = entityManager.createQuery(query);

        return typedQuery.getResultList();
    }

    @Override
    public List<Flight> findFlightsWithNewAirplanes() {
        return flightRepository.findFlightsWithNewAirplanes(LocalDate.now().minusYears(10));
    }


}
