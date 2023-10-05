package be.tsapasmi33.digitalcityairport.controllers;

import be.tsapasmi33.digitalcityairport.exceptions.ErrorResponse;
import be.tsapasmi33.digitalcityairport.models.dto.AirplaneDTO;
import be.tsapasmi33.digitalcityairport.models.entities.Airplane;
import be.tsapasmi33.digitalcityairport.models.entities.Airport;
import be.tsapasmi33.digitalcityairport.models.form.AirplaneForm;
import be.tsapasmi33.digitalcityairport.services.AirplaneService;
import be.tsapasmi33.digitalcityairport.services.AirplaneTypeService;
import be.tsapasmi33.digitalcityairport.services.AirportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Airplane Controller", description = "Manage airplanes")
@AllArgsConstructor
@RestController
@RequestMapping("/airplane")
public class AirplaneController {

    private final AirplaneService airplaneService;
    private final AirplaneTypeService airplaneTypeService;
    private final AirportService airportService;

    @Operation(summary = "Retrieves Airplanes", description = "Provides a list of all airplanes")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of airplanes",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AirplaneDTO.class))))
    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AirplaneDTO>> getAll() {
        List<Airplane> airplanes = airplaneService.getAll();

        return ResponseEntity.ok(
                airplanes.stream()
                        .map(AirplaneDTO::toDto)
                        .toList()
        );
    }

    @Operation(summary = "Get Airplane by Id", description = "Returns an airplane based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Airplane doesn't exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful retrieval of airplane", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AirplaneDTO.class)))
    })
    @GetMapping("/{id:^[0-9]+$}")
    public ResponseEntity<AirplaneDTO> getOne(@PathVariable long id) {
        return ResponseEntity.ok(
                AirplaneDTO.toDto(airplaneService.getOne(id))
        );
    }

    @Operation(summary = "Create an Airplane", description = "Creates an airplane from the provided payload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful submission", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Airplane Type or airport doesn't exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "201", description = "Successful creation of an airplane", content = @Content)
    })
    @PostMapping(value = "/add", params = {"typeId", "airportId"})
    public ResponseEntity<HttpStatus> add(@Valid @RequestBody AirplaneForm form) {
        Airplane airplane = form.toEntity();
        airplane.setType(airplaneTypeService.getOne(form.getTypeId()));
        airplane.setCurrentAirport(airportService.getOne(form.getAirportId()));
        airplaneService.insert(airplane);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @Operation(summary = "Set the current airport of the Airplane", description = "Sets the airport where the airplane is located based on an airport ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Airplane doesn't exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "204", description = "Successful setting of current airport", content = @Content)
    })
    @PatchMapping(value = "/{id:^[0-9]+$}", params = "airportId")
    public ResponseEntity<HttpStatus> updateCurrentAirport(@PathVariable long id, @RequestParam long airportId) {
        Airport airport = airportService.getOne(airportId);
        airplaneService.setCurrentAirport(id, airport);
        return ResponseEntity.noContent()
                .build();
    }

    @Operation(summary = "Delete an Airplane", description = "Deletes an airplane based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Airplane doesn't exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "204", description = "Successful deletion of airplane", content = @Content)
    })
    @DeleteMapping("/{id:^[0-9]+$}")
    public ResponseEntity<HttpStatus> delete(@PathVariable long id) {
        airplaneService.delete(id);

        return ResponseEntity.noContent()
                .build();
    }

}
