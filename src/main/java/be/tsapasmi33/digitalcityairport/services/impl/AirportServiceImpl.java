package be.tsapasmi33.digitalcityairport.services.impl;

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
                .orElseThrow(() -> new IllegalArgumentException("No airport with this id!"));
    }

    @Override
    public void insert(Airport entity) {
        airportRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        airportRepository.deleteById(id);
    }

    @Override
    public Airport update(Long id, Airport entity) {
        if (!airportRepository.existsById(id)) {
            throw new IllegalArgumentException("No airport with this id!");
        }
        entity.setId(id);
        return airportRepository.save(entity);
    }

    @Override
    public Airport findByCode(String code) {
        return airportRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("No airport with this id!"));
    }
}
