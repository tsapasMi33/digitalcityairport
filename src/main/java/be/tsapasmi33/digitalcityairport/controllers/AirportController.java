package be.tsapasmi33.digitalcityairport.controllers;

import be.tsapasmi33.digitalcityairport.models.dto.AirportDTO;
import be.tsapasmi33.digitalcityairport.models.entities.Airport;
import be.tsapasmi33.digitalcityairport.models.form.AirportForm;
import be.tsapasmi33.digitalcityairport.services.AirportService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/airport")
public class AirportController {

    private final AirportService airportService;

    @GetMapping("/all")
    public ResponseEntity<List<AirportDTO>> getAll() {
        return ResponseEntity.ok(
                airportService.getAll().stream()
                        .map(AirportDTO::toDto)
                        .toList());
    }

    @GetMapping("/{id:^[0-9]+$}")
    public ResponseEntity<AirportDTO> getOne(@PathVariable long id) {
        return ResponseEntity.ok(AirportDTO.toDto(airportService.getOne(id)));
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody AirportForm form) {
        airportService.insert(form.toEntity());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id:^[0-9]+$}")
    public ResponseEntity<AirportDTO> update(@PathVariable long id, @RequestBody AirportForm form) {
        Airport updated = airportService.update(id, form.toEntity());
        return ResponseEntity.ok(AirportDTO.toDto(updated));
    }

    @DeleteMapping("/{id:^[0-9]+$}")
    public ResponseEntity<HttpStatus> delete(@PathVariable long id) {
        airportService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
