package be.tsapasmi33.digitalcityairport.controllers;

import be.tsapasmi33.digitalcityairport.models.dto.PilotDTO;
import be.tsapasmi33.digitalcityairport.models.entities.Pilot;
import be.tsapasmi33.digitalcityairport.models.form.PilotForm;
import be.tsapasmi33.digitalcityairport.services.PilotService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/pilot")
public class PilotController {

    private final PilotService pilotService;

    @GetMapping("/all")
    public ResponseEntity<List<PilotDTO>> getAll() {
        return ResponseEntity.ok(
                pilotService.getAll().stream()
                        .map(PilotDTO::toDto)
                        .toList()
        );
    }

    @GetMapping("/{id:^[0-9]+$}")
    public ResponseEntity<PilotDTO> getOne(@PathVariable long id) {
        return ResponseEntity.ok(
                PilotDTO.toDto(pilotService.getOne(id))
        );
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody PilotForm form) {
        pilotService.insert(form.toEntity());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id:^[0-9]+$}")
    public ResponseEntity<PilotDTO> update(@PathVariable long id, @RequestBody PilotForm form) {
        Pilot updated = pilotService.update(id, form.toEntity());

        return ResponseEntity.ok(PilotDTO.toDto(updated));
    }

    @PatchMapping(path = "/{id:^[0-9]+$}", params = "airplaneTypeId")
    public ResponseEntity<HttpStatus> giveLicence(@PathVariable long id, @RequestParam long airplaneTypeId) {
        pilotService.addLicence(id, airplaneTypeId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id:^[0-9]+$}")
    public ResponseEntity<HttpStatus> delete(@PathVariable long id) {
        pilotService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
