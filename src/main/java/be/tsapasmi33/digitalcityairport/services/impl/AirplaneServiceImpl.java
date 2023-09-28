package be.tsapasmi33.digitalcityairport.services.impl;

import be.tsapasmi33.digitalcityairport.models.entities.Airplane;
import be.tsapasmi33.digitalcityairport.repositories.AirplaneRepository;
import be.tsapasmi33.digitalcityairport.repositories.AirplaneTypeRepository;
import be.tsapasmi33.digitalcityairport.services.AirplaneService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AirplaneServiceImpl implements AirplaneService {
    AirplaneRepository airplaneRepository;
    AirplaneTypeRepository airplaneTypeRepository;

    @Override
    public List<Airplane> getAll() {
        return airplaneRepository.findAll();
    }

    @Override
    public Airplane getOne(Long id) {
        return airplaneRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No airplane with id: " + id + " exists!"));
    }

    @Override
    public void insert(Airplane entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Airplane update(Long id, Airplane entity) {
        return null;
    }

    @Override
    public Airplane findBySerialNo(String serialNo) {
        return null;
    }
}
