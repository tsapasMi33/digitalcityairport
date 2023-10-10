package be.tsapasmi33.digitalcityairport.services.impl;

import be.tsapasmi33.digitalcityairport.exceptions.ResourceNotFoundException;
import be.tsapasmi33.digitalcityairport.models.entities.Flight;
import be.tsapasmi33.digitalcityairport.models.entities.Passenger;
import be.tsapasmi33.digitalcityairport.models.entities.enums.FidelityStatus;
import be.tsapasmi33.digitalcityairport.repositories.PassengerRepository;
import be.tsapasmi33.digitalcityairport.services.PassengerService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;

    @Override
    public List<Passenger> getAll() {
        return passengerRepository.findAll();
    }

    @Override
    public Passenger getOne(Long id) {
        return passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Passenger.class, id));
    }

    @Override
    public void insert(Passenger entity) {
        passengerRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        if (!passengerRepository.existsById(id)) {
            throw new ResourceNotFoundException(Passenger.class, id);
        }
        passengerRepository.deleteById(id);
    }

    @Override
    public Passenger update(Long id, Passenger entity) {
        Passenger old = getOne(id);
        entity.setId(old.getId());
        entity.setFidelity(old.getFidelity());
        entity.setReservations(old.getReservations());
        return passengerRepository.save(entity);
    }

    @Override
    public void updateFidelity(long id, FidelityStatus fidelity) {
        passengerRepository.updateFidelity(id, fidelity);
    }

    @Override
    public List<Flight> getReservedFlights(long passengerId, Boolean includeCancelled) {
        if (includeCancelled == null) {
            includeCancelled = false;
        } else if (includeCancelled) {
            includeCancelled = null;
        }
        return passengerRepository.findFlightsByPassenger(passengerId, includeCancelled);
    }

    @Override
    public Page<Passenger> getByFidelity(FidelityStatus fidelityStatus, int pageNumber, int countByPage) {
        return passengerRepository.findAllByFidelity(fidelityStatus, PageRequest.of(pageNumber,countByPage));
    }
}
