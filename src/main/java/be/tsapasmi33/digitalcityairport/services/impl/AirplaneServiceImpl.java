package be.tsapasmi33.digitalcityairport.services.impl;

import be.tsapasmi33.digitalcityairport.exceptions.AirplaneNotFoundException;
import be.tsapasmi33.digitalcityairport.models.entities.Airplane;
import be.tsapasmi33.digitalcityairport.models.entities.Airport;
import be.tsapasmi33.digitalcityairport.repositories.AirplaneRepository;
import be.tsapasmi33.digitalcityairport.services.AirplaneService;
import be.tsapasmi33.digitalcityairport.services.AirportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class AirplaneServiceImpl implements AirplaneService {
    AirplaneRepository airplaneRepository;
    AirportService airportService;

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
    public boolean isAvailable(Airplane airplane, LocalDateTime departure, LocalDateTime arrival, Airport origin) {
        long result = airplane.getFlights().stream()
                .filter(flight -> !(departure.isAfter(flight.getDeparture()) && departure.isBefore(flight.getArrival()))) //filter out flights that fly the hours in question
                .filter(flight -> !(arrival.isAfter(flight.getDeparture()) && arrival.isBefore(flight.getArrival())))     // same
                .filter(flight -> flight.getDestination() != origin && flight.getArrival().isBefore(departure))          // filter out flights that do not land at the airport in question before the time of departure
                .count();

        return result == 0;
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
    public void setCurrentAirport(long id, long airportId) {
        if (!airplaneRepository.existsById(id)) {
            throw new AirplaneNotFoundException(id);
        }
        Airport airport = airportService.getOne(airportId);

        airplaneRepository.setCurrentAirport(id, airport);
    }
}
