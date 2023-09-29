package be.tsapasmi33.digitalcityairport.services.impl;

import be.tsapasmi33.digitalcityairport.models.entities.AirplaneType;
import be.tsapasmi33.digitalcityairport.services.AirplaneTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AirplaneTypeServiceImpl implements AirplaneTypeService {
    @Override
    public List<AirplaneType> getAll() {
        return null;
    }

    @Override
    public AirplaneType getOne(Long id) {
        return null;
    }

    @Override
    public void insert(AirplaneType entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public AirplaneType update(Long id, AirplaneType entity) {
        return null;
    }
}
