package be.tsapasmi33.digitalcityairport.controllers;

import be.tsapasmi33.digitalcityairport.models.dto.AirplaneTypeDTO;
import be.tsapasmi33.digitalcityairport.models.entities.AirplaneType;
import be.tsapasmi33.digitalcityairport.models.form.AirplaneTypeForm;
import be.tsapasmi33.digitalcityairport.services.AirplaneTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/airplaneType")
public class AirplaneTypeController {

    private final AirplaneTypeService airplaneTypeService;

    @GetMapping("/all")
    public ResponseEntity<List<AirplaneTypeDTO>> getAll() {
        return ResponseEntity.ok(airplaneTypeService.getAll().stream()
                .map(AirplaneTypeDTO::toDto)
                .toList());
    }

    @GetMapping("/{id:^[0-9]+$}")
    public ResponseEntity<AirplaneTypeDTO> getOne(@PathVariable long id) {
        return ResponseEntity.ok(
                AirplaneTypeDTO.toDto(airplaneTypeService.getOne(id)));
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody AirplaneTypeForm form) {
        airplaneTypeService.insert(form.toEntity());

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("/{id:^[0-9]+$}")
    public ResponseEntity<AirplaneTypeDTO> update(@PathVariable long id, @RequestBody AirplaneTypeForm form) {
        AirplaneType updated = airplaneTypeService.update(id, form.toEntity());
        return ResponseEntity.ok(AirplaneTypeDTO.toDto(updated));
    }

    @DeleteMapping("/{id:^[0-9]+$}")
    public ResponseEntity<HttpStatus> delete(@PathVariable long id) {
        airplaneTypeService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
