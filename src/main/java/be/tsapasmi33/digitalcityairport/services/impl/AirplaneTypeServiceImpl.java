package be.tsapasmi33.digitalcityairport.services.impl;

import be.tsapasmi33.digitalcityairport.services.AirplaneTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AirplaneTypeServiceImpl implements AirplaneTypeService {
    @Override
    public List<AirplaneTypeService> getAll() {
        return null;
    }

    @Override
    public AirplaneTypeService getOne(Long id) {
        return null;
    }

    @Override
    public void insert(AirplaneTypeService entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public AirplaneTypeService update(Long id, AirplaneTypeService entity) {
        return null;
    }
}
