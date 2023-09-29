package be.tsapasmi33.digitalcityairport.services.impl;

import be.tsapasmi33.digitalcityairport.models.entities.Airplane;
import be.tsapasmi33.digitalcityairport.models.entities.Airport;
import be.tsapasmi33.digitalcityairport.repositories.AirplaneRepository;
import be.tsapasmi33.digitalcityairport.repositories.AirportRepository;
import be.tsapasmi33.digitalcityairport.services.AirplaneService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class AirplaneServiceImpl implements AirplaneService {
    AirplaneRepository airplaneRepository;
    AirportRepository airportRepository;

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
        airplaneRepository.deleteById(id);
    }

    @Override
    public Airplane update(Long id, Airplane entity) {
        return null;
    }

    @Override
    public Airplane findBySerialNo(String serialNo) {
        return null;
    }

    @Override
    public void setCurrentAirport(long id, long airportId) {
        if (!airplaneRepository.existsById(id)) {
            throw new IllegalArgumentException("Airplane does not exist");
        }
        if (!airportRepository.existsById(airportId)) {
            throw new IllegalArgumentException("Airport does not exist");
        }
        airplaneRepository.setCurrentAirport(id, airportId);
    }
}
