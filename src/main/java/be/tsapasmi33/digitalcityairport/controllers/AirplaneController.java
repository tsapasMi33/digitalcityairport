package be.tsapasmi33.digitalcityairport.controllers;

import be.tsapasmi33.digitalcityairport.models.dto.AirplaneDTO;
import be.tsapasmi33.digitalcityairport.models.entities.Airplane;
import be.tsapasmi33.digitalcityairport.models.form.AirplaneForm;
import be.tsapasmi33.digitalcityairport.services.AirplaneService;
import be.tsapasmi33.digitalcityairport.services.AirplaneTypeService;
import be.tsapasmi33.digitalcityairport.services.AirportService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/airplane")
public class AirplaneController {

    private final AirplaneService airplaneService;
    private final AirplaneTypeService airplaneTypeService;
    private final AirportService airportService;

    @GetMapping("/all")
    public ResponseEntity<List<AirplaneDTO>> getAll() {
        List<Airplane> airplanes = airplaneService.getAll();

        return ResponseEntity.ok(
                airplanes.stream()
                        .map(AirplaneDTO::toDto)
                        .toList()
        );
    }

    @GetMapping("/{id:^[0-9]+$}")
    public ResponseEntity<AirplaneDTO> getOne(@PathVariable long id) {
        return ResponseEntity.ok(
                AirplaneDTO.toDto(airplaneService.getOne(id))
        );
    }

    @PostMapping(value = "/add", params = {"typeId", "airportId"})
    public ResponseEntity<HttpStatus> add(@RequestBody AirplaneForm form, @RequestParam Long typeId, @RequestParam Long airportId) {
        Airplane airplane = form.toEntity();
        airplane.setType(airplaneTypeService.getOne(typeId));
        airplane.setCurrentAirport(airportService.getOne(airportId));
        airplaneService.insert(airplane);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("/{id:^[0-9]+$}")
    public ResponseEntity<AirplaneDTO> update(@PathVariable long id, @RequestBody AirplaneForm form) {
        Airplane updated = airplaneService.update(id, form.toEntity());
        return ResponseEntity.ok(AirplaneDTO.toDto(updated));
    }

    @PatchMapping(value = "/{id:^[0-9]+$}", params = "airportId")
    public ResponseEntity<HttpStatus> updateCurrentAirport(@PathVariable long id, @RequestParam long airportId) {
        airplaneService.setCurrentAirport(id, airportId);
        return ResponseEntity.noContent()
                .build();
    }

    @DeleteMapping("/{id:^[0-9]+$}")
    public ResponseEntity<HttpStatus> delete(@PathVariable long id) {
        airplaneService.delete(id);

        return ResponseEntity.noContent()
                .build();
    }

}
