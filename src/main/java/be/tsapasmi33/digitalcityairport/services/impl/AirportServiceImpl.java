package be.tsapasmi33.digitalcityairport.services.impl;

import be.tsapasmi33.digitalcityairport.exceptions.ResourceNotFoundException;
import be.tsapasmi33.digitalcityairport.models.entities.Airport;
import be.tsapasmi33.digitalcityairport.repositories.AirportRepository;
import be.tsapasmi33.digitalcityairport.services.AirportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;

    @Override
    public List<Airport> getAll() {
        return airportRepository.findAll();
    }

    @Override
    public Airport getOne(Long id) {
        return airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Airport.class, id));
    }

    @Override
    public void insert(Airport entity) {
        airportRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        if (!airportRepository.existsById(id)) {
            throw new ResourceNotFoundException(Airport.class, id);
        }
        airportRepository.deleteById(id);
    }

    @Override
    public Airport update(Long id, Airport entity) {
        Airport old = getOne(id);
        entity.setId(old.getId());
        entity.setAirplanesIn(old.getAirplanesIn());
        return airportRepository.save(entity);
    }

    @Override
    public Airport findByCode(String code) {
        return airportRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Airport with code: " + code + " does not exist!"));
    }
}
