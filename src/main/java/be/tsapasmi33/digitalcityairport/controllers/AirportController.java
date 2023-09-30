package be.tsapasmi33.digitalcityairport.controllers;

import be.tsapasmi33.digitalcityairport.exceptions.ErrorResponse;
import be.tsapasmi33.digitalcityairport.models.dto.AirportDTO;
import be.tsapasmi33.digitalcityairport.models.entities.Airport;
import be.tsapasmi33.digitalcityairport.models.form.AirportForm;
import be.tsapasmi33.digitalcityairport.services.AirportService;
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

@Tag(name = "Airport Controller", description = "Manage airports")
@AllArgsConstructor
@RestController
@RequestMapping("/airport")
public class AirportController {

    private final AirportService airportService;

    @Operation(summary = "Retrieves Airports", description = "Provides a list of all airports")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of airports",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AirportDTO.class))))
    @GetMapping("/all")
    public ResponseEntity<List<AirportDTO>> getAll() {
        return ResponseEntity.ok(
                airportService.getAll().stream()
                        .map(AirportDTO::toDto)
                        .toList());
    }

    @Operation(summary = "Get Airport by Id", description = "Returns an airport based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Airport doesn't exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful retrieval of airport", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AirportDTO.class)))
    })
    @GetMapping("/{id:^[0-9]+$}")
    public ResponseEntity<AirportDTO> getOne(@PathVariable long id) {
        return ResponseEntity.ok(AirportDTO.toDto(airportService.getOne(id)));
    }

    @Operation(summary = "Create an Airport", description = "Creates an airport from the provided payload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful submission", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Airport doesn't exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "201", description = "Successful creation of an airport", content = @Content)
    })
    @PostMapping(path = "/add")
    public ResponseEntity<HttpStatus> add(@RequestBody AirportForm form) {
        airportService.insert(form.toEntity());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Update Airport by Id", description = "Updates an airport based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Airport doesn't exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful update of airport", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AirportDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful submission", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id:^[0-9]+$}")
    public ResponseEntity<AirportDTO> update(@PathVariable long id, @RequestBody AirportForm form) {
        Airport updated = airportService.update(id, form.toEntity());
        return ResponseEntity.ok(AirportDTO.toDto(updated));
    }

    @Operation(summary = "Delete an Airport", description = "Deletes an airport based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Airport doesn't exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "204", description = "Successful deletion of airport", content = @Content)
    })
    @DeleteMapping("/{id:^[0-9]+$}")
    public ResponseEntity<HttpStatus> delete(@PathVariable long id) {
        airportService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
