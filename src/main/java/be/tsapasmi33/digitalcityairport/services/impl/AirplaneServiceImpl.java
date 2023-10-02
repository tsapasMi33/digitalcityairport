package be.tsapasmi33.digitalcityairport.services.impl;

import be.tsapasmi33.digitalcityairport.exceptions.AirplaneNotFoundException;
import be.tsapasmi33.digitalcityairport.exceptions.ResourceNotAvailableException;
import be.tsapasmi33.digitalcityairport.models.entities.Airplane;
import be.tsapasmi33.digitalcityairport.models.entities.Airport;
import be.tsapasmi33.digitalcityairport.repositories.AirplaneRepository;
import be.tsapasmi33.digitalcityairport.services.AirplaneService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class AirplaneServiceImpl implements AirplaneService {
    AirplaneRepository airplaneRepository;

    @Override
    public List<Airplane> getAll() {
        return airplaneRepository.findAll();
    }

    @Override
    public Airplane getOne(Long id) {
        return airplaneRepository.findById(id)
                .orElseThrow(() -> new AirplaneNotFoundException(id));
    }

    @Override
    public void insert(Airplane entity) {
        airplaneRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        if (!airplaneRepository.existsById(id)) {
            throw new AirplaneNotFoundException(id);
        }
        airplaneRepository.deleteById(id);
    }

    @Override
    public Airplane update(Long id, Airplane entity) {
        Airplane old = getOne(id);
        entity.setId(id);
        entity.setType(old.getType());
        entity.setFlights(old.getFlights());
        entity.setCurrentAirport(old.getCurrentAirport());

        return airplaneRepository.save(entity);
    }

    @Override
    public Airplane findBySerialNo(String serialNo) {
        return null;
    }

    @Override
    public void setCurrentAirport(long id, Airport airport) {
        if (!airplaneRepository.existsById(id)) {
            throw new AirplaneNotFoundException(id);
        }

        airplaneRepository.setCurrentAirport(id, airport);
    }

    @Override
    public Airplane getIfAvailable(long airplaneId, LocalDateTime departure, LocalDateTime arrival) {
        Airplane airplane = getOne(airplaneId);

        long overlapping = airplane.getFlights().stream()
                .filter(flight -> departure.isBefore(flight.getArrival()) && arrival.isAfter(flight.getDeparture()))
                .filter(flight -> departure.isAfter(flight.getDeparture()) && arrival.isBefore(flight.getArrival()))
                .filter(flight -> !flight.isCancelled())
                .count();

        if (overlapping > 0) {
            throw new ResourceNotAvailableException(Airplane.class, airplaneId, "on these dates");
        }

        return airplane;
    }
}
