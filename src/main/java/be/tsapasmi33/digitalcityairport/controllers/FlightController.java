package be.tsapasmi33.digitalcityairport.controllers;

import be.tsapasmi33.digitalcityairport.models.dto.FlightDTO;
import be.tsapasmi33.digitalcityairport.models.entities.Airplane;
import be.tsapasmi33.digitalcityairport.models.entities.Flight;
import be.tsapasmi33.digitalcityairport.models.form.FlightForm;
import be.tsapasmi33.digitalcityairport.services.AirplaneService;
import be.tsapasmi33.digitalcityairport.services.AirportService;
import be.tsapasmi33.digitalcityairport.services.FlightService;
import be.tsapasmi33.digitalcityairport.services.PilotService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/flight")
public class FlightController {

    private final FlightService flightService;
    private final PilotService pilotService;
    private final AirplaneService airplaneService;
    private final AirportService airportService;

    @GetMapping("/all") // TODO params: from, to, date [all optional]
    public ResponseEntity<List<FlightDTO>> getAll() {
        return ResponseEntity.ok(
                flightService.getAll().stream()
                        .map(FlightDTO::toDto)
                        .toList()
        );
    }

    @GetMapping(path = "/{id:^[0-9]+$}", params = {"pilotId", "airplaneId", "originAirportId", "destinationAirportId"})
    public ResponseEntity<FlightDTO> getOne(@PathVariable long id) {
        return ResponseEntity.ok(
                FlightDTO.toDto(flightService.getOne(id))
        );
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody FlightForm form,
                                          @RequestParam long pilotId,
                                          @RequestParam long airplaneId,
                                          @RequestParam long originAirportId,
                                          @RequestParam long destinationAirportId) {
        Flight flight = form.toEntity();
        flight.setPilot(pilotService.getOne(pilotId));
        flight.setAirplane(airplaneService.getOne(airplaneId));
        flight.setOrigin(airportService.getOne(originAirportId));
        flight.setDestination(airportService.getOne(destinationAirportId));

        flightService.insert(flight);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(path = "/{id:^[0-9]+$}", params = {"pilotId", "airplaneId"})
    public ResponseEntity<FlightDTO> update(@PathVariable long id,
                                            @RequestBody FlightForm form,
                                            @RequestParam(required = false) Long pilotId,
                                            @RequestParam(required = false) Long airplaneId) {
        Flight newFlight = form.toEntity();
        if (pilotId != null) {
            newFlight.setPilot(pilotService.getOne(pilotId));
        }
        if (airplaneId != null) {
            Airplane airplane = airplaneService.getOne(airplaneId);
            if (airplaneService.isAvailable(airplane, newFlight.getDeparture(), newFlight.getArrival(), newFlight.getOrigin())) {
                newFlight.setAirplane(airplane);
            }
        }

        Flight updated = flightService.update(id, newFlight);

        return ResponseEntity.ok(FlightDTO.toDto(updated));
    }

    @PatchMapping("/{id:^[0-9]+$}/cancel")
    public ResponseEntity<HttpStatus> cancelFlight(@PathVariable long id) {
        flightService.cancelFlight(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id:^[0-9]+$}")
    public ResponseEntity<HttpStatus> delete(@PathVariable long id) {
        flightService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
