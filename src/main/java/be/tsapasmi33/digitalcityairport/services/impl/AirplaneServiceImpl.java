package be.tsapasmi33.digitalcityairport.services.impl;

import be.tsapasmi33.digitalcityairport.exceptions.ResourceNotFoundException;
import be.tsapasmi33.digitalcityairport.models.entities.Airplane;
import be.tsapasmi33.digitalcityairport.models.entities.Airport;
import be.tsapasmi33.digitalcityairport.repositories.AirplaneRepository;
import be.tsapasmi33.digitalcityairport.repositories.FlightRepository;
import be.tsapasmi33.digitalcityairport.services.AirplaneService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AirplaneServiceImpl implements AirplaneService {
    AirplaneRepository airplaneRepository;
    FlightRepository flightRepository;

    @Override
    public List<Airplane> getAll() {
        return airplaneRepository.findAll();
    }

    @Override
    public Airplane getOne(Long id) {
        return airplaneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Airplane.class, id));
    }

    @Override
    public void insert(Airplane entity) {
        airplaneRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        if (!airplaneRepository.existsById(id)) {
            throw new ResourceNotFoundException(Airplane.class, id);
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
            throw new ResourceNotFoundException(Airplane.class, id);
        }

        airplaneRepository.setCurrentAirport(id, airport);
    }
}
