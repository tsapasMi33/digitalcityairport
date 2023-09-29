package be.tsapasmi33.digitalcityairport.controllers;

import be.tsapasmi33.digitalcityairport.models.dto.PassengerDTO;
import be.tsapasmi33.digitalcityairport.models.entities.Passenger;
import be.tsapasmi33.digitalcityairport.models.entities.enums.FidelityStatus;
import be.tsapasmi33.digitalcityairport.models.form.PassengerForm;
import be.tsapasmi33.digitalcityairport.services.PassengerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/passenger")
public class PassengerController {

    private final PassengerService passengerService;

    @GetMapping("/all")
    public ResponseEntity<List<PassengerDTO>> getAll() {
        return ResponseEntity.ok(
                passengerService.getAll().stream()
                        .map(PassengerDTO::toDTO)
                        .toList()
        );
    }

    @GetMapping("/{id:^[0-9]+$}")
    public ResponseEntity<PassengerDTO> getOne(@PathVariable long id) {
        return ResponseEntity.ok(PassengerDTO.toDTO(passengerService.getOne(id)));
    }

    @PostMapping({"", "/add"})
    public ResponseEntity<HttpStatus> add(@RequestBody PassengerForm form) {
        Passenger passenger = form.toEntity();
        passenger.setFidelity(FidelityStatus.NONE);
        passengerService.insert(passenger);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id:^[0-9]+$}")
    public ResponseEntity<PassengerDTO> update(@PathVariable long id, @RequestBody PassengerForm form) {
        Passenger updated = passengerService.update(id, form.toEntity());
        return ResponseEntity.ok(PassengerDTO.toDTO(updated));
    }

    @PatchMapping(value = "/{id:^[0-9]+$}", params = "fidelity")
    public ResponseEntity<HttpStatus> updateFidelity(@PathVariable long id, @RequestParam FidelityStatus fidelity) {
        passengerService.updateFidelity(id, fidelity);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id:^[0-9]+$}")
    public ResponseEntity<HttpStatus> delete(@PathVariable long id) {
        passengerService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
