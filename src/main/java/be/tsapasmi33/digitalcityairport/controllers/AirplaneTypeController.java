package be.tsapasmi33.digitalcityairport.controllers;

import be.tsapasmi33.digitalcityairport.exceptions.ErrorResponse;
import be.tsapasmi33.digitalcityairport.models.dto.AirplaneDTO;
import be.tsapasmi33.digitalcityairport.models.dto.AirplaneTypeDTO;
import be.tsapasmi33.digitalcityairport.models.entities.AirplaneType;
import be.tsapasmi33.digitalcityairport.models.form.AirplaneTypeForm;
import be.tsapasmi33.digitalcityairport.services.AirplaneTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Airplane Type Controller", description = "Manage airplane types")
@AllArgsConstructor
@RestController
@RequestMapping("/airplane-type")
public class AirplaneTypeController {

    private final AirplaneTypeService airplaneTypeService;

    @Operation(summary = "Retrieves Airplane Types ", description = "Provides a list of all airplane types")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of airplane types",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AirplaneTypeDTO.class))))
    @GetMapping("/all")
    public ResponseEntity<List<AirplaneTypeDTO>> getAll() {
        return ResponseEntity.ok(airplaneTypeService.getAll().stream()
                .map(AirplaneTypeDTO::toDto)
                .toList());
    }

    @Operation(summary = "Get Airplane Type by Id", description = "Returns an airplane type based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Airplane Type doesn't exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful retrieval of Airplane Type", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AirplaneTypeDTO.class)))
    })
    @GetMapping("/{id:^[0-9]+$}")
    public ResponseEntity<AirplaneTypeDTO> getOne(@PathVariable long id) {
        return ResponseEntity.ok(
                AirplaneTypeDTO.toDto(airplaneTypeService.getOne(id)));
    }

    @Operation(summary = "Create an Airplane Type", description = "Creates an airplane type from the provided payload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful submission", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "201", description = "Successful creation of an airplane type", content = @Content)
    })
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody AirplaneTypeForm form) {
        airplaneTypeService.insert(form.toEntity());

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @Operation(summary = "Update Airplane Type by Id", description = "Updates an airplane type based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Airplane type doesn't exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful update of airplane type", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AirplaneDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful submission", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id:^[0-9]+$}")
    public ResponseEntity<AirplaneTypeDTO> update(@PathVariable long id, @RequestBody AirplaneTypeForm form) {
        AirplaneType updated = airplaneTypeService.update(id, form.toEntity());
        return ResponseEntity.ok(AirplaneTypeDTO.toDto(updated));
    }

    @Operation(summary = "Delete an Airplane Type", description = "Deletes an airplane Type based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Airplane type doesn't exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "204", description = "Successful deletion of airplane type", content = @Content)
    })
    @DeleteMapping("/{id:^[0-9]+$}")
    public ResponseEntity<HttpStatus> delete(@PathVariable long id) {
        airplaneTypeService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
