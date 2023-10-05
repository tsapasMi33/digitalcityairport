package be.tsapasmi33.digitalcityairport.controllers;

import be.tsapasmi33.digitalcityairport.exceptions.ErrorResponse;
import be.tsapasmi33.digitalcityairport.models.dto.FlightDTO;
import be.tsapasmi33.digitalcityairport.models.entities.Flight;
import be.tsapasmi33.digitalcityairport.models.form.FlightForm;
import be.tsapasmi33.digitalcityairport.services.AirplaneService;
import be.tsapasmi33.digitalcityairport.services.AirportService;
import be.tsapasmi33.digitalcityairport.services.FlightService;
import be.tsapasmi33.digitalcityairport.services.PilotService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Flight Controller", description = "Manage flights")
@AllArgsConstructor
@RestController
@RequestMapping("/flight")
public class FlightController {

    private final FlightService flightService;
    private final PilotService pilotService;
    private final AirplaneService airplaneService;
    private final AirportService airportService;

    @Operation(summary = "Retrieves Flights", description = "Provides a list of all flights")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of airplanes",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = FlightDTO.class))))
    @GetMapping("/all") // TODO params: from, to, date [all optional]
    public ResponseEntity<List<FlightDTO>> getAll() {
        return ResponseEntity.ok(
                flightService.getAll().stream()
                        .map(FlightDTO::toDto)
                        .toList()
        );
    }

    @Operation(summary = "Get Flight by Id", description = "Returns a flight based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Flight doesn't exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful retrieval of flight", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FlightDTO.class)))
    })
    @GetMapping("/{id:^[0-9]+$}")
    public ResponseEntity<FlightDTO> getOne(@PathVariable long id) {
        return ResponseEntity.ok(
                FlightDTO.toDto(flightService.getOne(id))
        );
    }

    @Operation(summary = "Create a Flight", description = "Creates a flight from the provided payload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful submission", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Pilot, airplane or airport doesn't exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "201", description = "Successful creation of a flight", content = @Content)
    })
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@Valid @RequestBody FlightForm form) {
        Flight flight = form.toEntity();
        flight.setAirplane(airplaneService.getOne(form.getAirplaneId()));
        flight.setPilot(pilotService.getOne(form.getPilotId()));
        flight.setOrigin(airportService.getOne(form.getOriginAirportId()));
        flight.setDestination(airportService.getOne(form.getDestinationAirportId()));

        flightService.insert(flight);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Update Flight by Id", description = "Updates a flight based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Pilot, airplane or airport doesn't exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful update of flight", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FlightDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful submission", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping(path = "/{id:^[0-9]+$}", params = {"pilotId", "airplaneId"})
    public ResponseEntity<FlightDTO> update(@PathVariable long id, @Valid @RequestBody FlightForm form) {
        Flight newFlight = form.toEntity();
        if (form.getPilotId() != null) {
            newFlight.setPilot(pilotService.getOne(form.getPilotId()));
        }
        if (form.getAirplaneId() != null) {
            newFlight.setAirplane(airplaneService.getOne(form.getAirplaneId()));
        }

        Flight updated = flightService.update(id, newFlight);

        return ResponseEntity.ok(FlightDTO.toDto(updated));
    }

    @Operation(summary = "Cancel a Flight", description = "Cancels a flight based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful cancel", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Flight doesn't exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "204", description = "Successful cancel of flight", content = @Content)
    })
    @PatchMapping("/{id:^[0-9]+$}/cancel")
    public ResponseEntity<HttpStatus> cancelFlight(@PathVariable long id) {
        flightService.cancelFlight(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete a Flight", description = "Deletes a flight based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Flight doesn't exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "204", description = "Successful deletion of flight", content = @Content)
    })
    @DeleteMapping("/{id:^[0-9]+$}")
    public ResponseEntity<HttpStatus> delete(@PathVariable long id) {
        flightService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
