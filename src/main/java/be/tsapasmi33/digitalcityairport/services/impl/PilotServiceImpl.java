package be.tsapasmi33.digitalcityairport.services.impl;

import be.tsapasmi33.digitalcityairport.models.entities.AirplaneType;
import be.tsapasmi33.digitalcityairport.models.entities.Pilot;
import be.tsapasmi33.digitalcityairport.repositories.AirplaneTypeRepository;
import be.tsapasmi33.digitalcityairport.repositories.PilotRepository;
import be.tsapasmi33.digitalcityairport.services.PilotService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PilotServiceImpl implements PilotService {

    private final PilotRepository pilotRepository;
    private final AirplaneTypeRepository airplaneTypeRepository;

    @Override
    public List<Pilot> getAll() {
        return pilotRepository.findAll();
    }

    @Override
    public Pilot getOne(Long id) {
        return pilotRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No pilot with this id!"));
    }

    @Override
    public void insert(Pilot entity) {
        pilotRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        pilotRepository.deleteById(id);
    }

    @Override
    public Pilot update(Long id, Pilot entity) {
        Pilot old = getOne(id);

        entity.setId(old.getId());
        entity.setLicences(old.getLicences());

        return pilotRepository.save(entity);
    }

    @Override
    public void addLicence(long pilotId, long airplaneTypeId) {

        AirplaneType airplaneType = airplaneTypeRepository.findById(airplaneTypeId)
                .orElseThrow(() -> new IllegalArgumentException("No airplane type with this id"));

        Pilot pilot = getOne(pilotId);
        pilot.getLicences().add(airplaneType);
    }
}
