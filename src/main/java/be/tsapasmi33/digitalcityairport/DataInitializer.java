package be.tsapasmi33.digitalcityairport;

import be.tsapasmi33.digitalcityairport.models.entities.*;
import be.tsapasmi33.digitalcityairport.models.entities.enums.FidelityStatus;
import be.tsapasmi33.digitalcityairport.repositories.*;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@AllArgsConstructor
@Component
public class DataInitializer {

    private final AirplaneRepository airplaneRepository;
    private final AirplaneTypeRepository airplaneTypeRepository;
    private final AirportRepository airportRepository;
    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;
    private final PilotRepository pilotRepository;
    private final ReservationRepository reservationRepository;


    @PostConstruct
    public void init() {
        AirplaneType airplaneType1 = new AirplaneType();
        airplaneType1.setMake("Boeing");
        airplaneType1.setModel("737");
        airplaneType1.setCapacity(300);
        airplaneTypeRepository.save(airplaneType1);

        AirplaneType airplaneType2 = new AirplaneType();
        airplaneType1.setMake("Airbus");
        airplaneType1.setModel("A320");
        airplaneType1.setCapacity(320);
        airplaneTypeRepository.save(airplaneType2);

        Airport airport1 = new Airport();
        airport1.setCode("BXL");
        airport1.setAddress("Zaventem");
        airport1.setName("Brussels international");
        airportRepository.save(airport1);

        Airport airport2 = new Airport();
        airport2.setCode("PRS");
        airport2.setAddress("Paris");
        airport2.setName("Charles De Gaulle");
        airportRepository.save(airport2);

        Airplane airplane1 = new Airplane();
        airplane1.setSerialNo("ABC12345678");
        airplane1.setConstructionDate(LocalDate.of(2018, 6, 1));
        airplane1.setType(airplaneTypeRepository.findById(1L).get());
        airplaneRepository.save(airplane1);

        Airplane airplane2 = new Airplane();
        airplane2.setSerialNo("XYZ12345678");
        airplane2.setConstructionDate(LocalDate.of(2020, 9, 3));
        airplane2.setType(airplaneTypeRepository.findById(2L).get());
        airplaneRepository.save(airplane2);

        Pilot pilot = new Pilot();
        pilot.setFirstname("John");
        pilot.setLastname("Doe");
        pilot.setIdNo("AB12345678");
        pilot.setLicences(new ArrayList<>());
        pilot.getLicences().add(airplaneTypeRepository.findById(1L).get());
        pilotRepository.save(pilot);

        Flight flight = new Flight();
        flight.setCode("AB1234");
        flight.setDeparture(LocalDateTime.of(2023, 11, 1, 12, 0, 0));
        flight.setArrival(LocalDateTime.of(2023, 11, 1, 13, 30, 0));
        flight.setPrice(30000);
        flight.setPilot(pilotRepository.findById(1L).get());
        flight.setAirplane(airplaneRepository.findById(1L).get());
        flight.setOrigin(airportRepository.findById(1L).get());
        flight.setDestination(airportRepository.findById(2L).get());
        flightRepository.save(flight);

        Passenger passenger = new Passenger();
        passenger.setFirstname("Jane");
        passenger.setLastname("Doe");
        passenger.setIdNo("AB12343678");
        passenger.setFidelity(FidelityStatus.NONE);
        passengerRepository.save(passenger);

        Reservation reservation = new Reservation();
        reservation.setPrice(100);
        reservation.setFlight(flightRepository.findById(1L).get());
        reservation.setPassenger(passengerRepository.findById(1L).get());
        reservationRepository.save(reservation);
    }
}
