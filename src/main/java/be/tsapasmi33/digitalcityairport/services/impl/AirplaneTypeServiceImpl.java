package be.tsapasmi33.digitalcityairport.services.impl;

import be.tsapasmi33.digitalcityairport.exceptions.AirplaneTypeNotFoundException;
import be.tsapasmi33.digitalcityairport.models.entities.AirplaneType;
import be.tsapasmi33.digitalcityairport.repositories.AirplaneTypeRepository;
import be.tsapasmi33.digitalcityairport.services.AirplaneTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AirplaneTypeServiceImpl implements AirplaneTypeService {

    AirplaneTypeRepository airplaneTypeRepository;

    @Override
    public List<AirplaneType> getAll() {
        return airplaneTypeRepository.findAll();
    }

    @Override
    public AirplaneType getOne(Long id) {
        return airplaneTypeRepository.findById(id)
                .orElseThrow(() -> new AirplaneTypeNotFoundException(id));
    }

    @Override
    public void insert(AirplaneType entity) {
        airplaneTypeRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        if (!airplaneTypeRepository.existsById(id)) {
            throw new AirplaneTypeNotFoundException(id);
        }
        airplaneTypeRepository.deleteById(id);
    }

    @Override
    public AirplaneType update(Long id, AirplaneType entity) {
        if (!airplaneTypeRepository.existsById(id)) {
            throw new AirplaneTypeNotFoundException(id);
        }
        entity.setId(id);
        return airplaneTypeRepository.save(entity);
    }
}
