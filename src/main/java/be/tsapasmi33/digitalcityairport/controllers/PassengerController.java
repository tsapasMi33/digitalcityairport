package be.tsapasmi33.digitalcityairport.controllers;

import be.tsapasmi33.digitalcityairport.exceptions.ErrorResponse;
import be.tsapasmi33.digitalcityairport.models.dto.FlightDTO;
import be.tsapasmi33.digitalcityairport.models.dto.PassengerDTO;
import be.tsapasmi33.digitalcityairport.models.entities.Passenger;
import be.tsapasmi33.digitalcityairport.models.entities.enums.FidelityStatus;
import be.tsapasmi33.digitalcityairport.models.form.PassengerForm;
import be.tsapasmi33.digitalcityairport.services.PassengerService;
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

@Tag(name = "Passenger Controller", description = "Manage passengers")
@AllArgsConstructor
@RestController
@RequestMapping("/passenger")
public class PassengerController {

    private final PassengerService passengerService;

    @Operation(summary = "Retrieves Passengers", description = "Provides a list of all passengers")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of passengers",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PassengerDTO.class))))
    @GetMapping("/all")
    public ResponseEntity<List<PassengerDTO>> getAll() {
        return ResponseEntity.ok(
                passengerService.getAll().stream()
                        .map(PassengerDTO::toDTO)
                        .toList()
        );
    }

    @Operation(summary = "Get Passenger by Id", description = "Returns a passenger based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Passenger doesn't exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful retrieval of passenger", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PassengerDTO.class)))
    })
    @GetMapping("/{id:^[0-9]+$}")
    public ResponseEntity<PassengerDTO> getOne(@PathVariable long id) {
        return ResponseEntity.ok(PassengerDTO.toDTO(passengerService.getOne(id)));
    }

    @Operation(summary = "Get Passenger's Flights", description = "Returns all flights from a passenger based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Passenger doesn't exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful retrieval of passenger's flights", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PassengerDTO.class)))
    })
    @GetMapping("/{id:^[0-9]+$}/flights")
    public ResponseEntity<List<FlightDTO>> getFlights(@PathVariable long id, @RequestParam(required = false) Boolean cancelled) {
        if (cancelled == null) {
            cancelled = false;
        }
        List<FlightDTO> flights = passengerService.getReservedFlights(id, cancelled).stream()
                .map(FlightDTO::toDto)
                .toList();
        return ResponseEntity.ok(flights);
    }

    @Operation(summary = "Create a Passenger", description = "Creates a passenger from the provided payload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful submission", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "201", description = "Successful creation of a passenger", content = @Content)
    })
    @PostMapping({"", "/add"})
    public ResponseEntity<HttpStatus> add(@Valid @RequestBody PassengerForm form) {
        Passenger passenger = form.toEntity();
        passenger.setFidelity(FidelityStatus.NONE);
        passengerService.insert(passenger);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Update Passenger by Id", description = "Updates a passenger based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Passenger doesn't exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful update of passenger", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PassengerDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful submission", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id:^[0-9]+$}")
    public ResponseEntity<PassengerDTO> update(@PathVariable long id, @Valid @RequestBody PassengerForm form) {
        Passenger updated = passengerService.update(id, form.toEntity());
        return ResponseEntity.ok(PassengerDTO.toDTO(updated));
    }

    @Operation(summary = "Set the fidelity of a Passenger", description = "Sets the fidelity of a passenger based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Passenger doesn't exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "204", description = "Successful setting of passenger's fidelity", content = @Content)
    })
    @PatchMapping(value = "/{id:^[0-9]+$}", params = "fidelity")
    public ResponseEntity<HttpStatus> updateFidelity(@PathVariable long id, @RequestParam FidelityStatus fidelity) {
        passengerService.updateFidelity(id, fidelity);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete a Passenger", description = "Deletes a passenger based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Passenger doesn't exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "204", description = "Successful deletion of passenger", content = @Content)
    })
    @DeleteMapping("/{id:^[0-9]+$}")
    public ResponseEntity<HttpStatus> delete(@PathVariable long id) {
        passengerService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
