package be.tsapasmi33.digitalcityairport.controllers;

import be.tsapasmi33.digitalcityairport.exceptions.ErrorResponse;
import be.tsapasmi33.digitalcityairport.models.dto.ReservationDTO;
import be.tsapasmi33.digitalcityairport.models.entities.Flight;
import be.tsapasmi33.digitalcityairport.models.entities.Passenger;
import be.tsapasmi33.digitalcityairport.models.entities.Reservation;
import be.tsapasmi33.digitalcityairport.models.form.ReservationForm;
import be.tsapasmi33.digitalcityairport.services.FlightService;
import be.tsapasmi33.digitalcityairport.services.PassengerService;
import be.tsapasmi33.digitalcityairport.services.ReservationService;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Reservation Controller", description = "Manage reservations")
@AllArgsConstructor
@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final FlightService flightService;
    private final PassengerService passengerService;

    @Operation(summary = "Retrieves Reservations", description = "Provides a list of all reservations. If parameters passed filters the list accordingly")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of reservations",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ReservationDTO.class))))
    @GetMapping(path = "/all")
    public ResponseEntity<List<ReservationDTO>> getAll(@RequestParam(required = false) LocalDate reservationDate,
                                                       @RequestParam(required = false) Boolean cancelled,
                                                       @RequestParam(required = false) Long flightId,
                                                       @RequestParam(required = false) Long passengerId) {

        Flight flight = flightService.getOne(flightId);
        Passenger passenger = passengerService.getOne(passengerId);
        return ResponseEntity.ok(
                reservationService.findAllByCriteria(reservationDate, cancelled, flight, passenger)
                        .stream()
                        .map(ReservationDTO::toDto)
                        .toList()
        );
    }

    @Operation(summary = "Get Reservation by Id", description = "Returns a reservation based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Reservation doesn't exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful retrieval of reservation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationDTO.class)))
    })
    @GetMapping("/{id:^[0-9]+$}")
    public ResponseEntity<ReservationDTO> getOne(@PathVariable long id) {
        return ResponseEntity.ok(ReservationDTO.toDto(reservationService.getOne(id)));
    }

    @Operation(summary = "Create a Reservation", description = "Creates a reservation from the provided payload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful submission", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Flight or passenger doesn't exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "201", description = "Successful creation of a reservation", content = @Content)
    })
    @PostMapping(path = "/create", params = {"price", "flightId", "passengerId"})
    public ResponseEntity<HttpStatus> create(@Valid @RequestBody ReservationForm form, @RequestParam double price, @RequestParam long flightId, @RequestParam long passengerId) {
        Reservation reservation = form.toEntity();
        reservation.setPrice(price);
        reservation.setFlight(flightService.getOne(flightId));
        reservation.setPassenger(passengerService.getOne(passengerId));

        reservationService.insert(reservation);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Update Reservation by Id", description = "Updates a reservation based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Reservation doesn't exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful update of reservation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful submission", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id:^[0-9]+$}") //TODO Check if useful
    public ResponseEntity<ReservationDTO> update(@PathVariable long id, @RequestBody ReservationForm form) {
        Reservation updated = reservationService.update(id, form.toEntity());
        return ResponseEntity.ok(ReservationDTO.toDto(updated));
    }

    @Operation(summary = "Cancel a Reservation", description = "Cancels a reservation based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful cancel", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Reservation doesn't exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "204", description = "Successful cancel of reservation", content = @Content)
    })
    @PatchMapping("/{id:^[0-9]+$}/cancel")
    public ResponseEntity<HttpStatus> cancel(@PathVariable long id) {
        if (!flightService.isDepartureAfterXDays(reservationService.getOne(id).getFlight().getId(), LocalDateTime.now().plusDays(3))) {
            throw new IllegalArgumentException("Flight is less than three days ahead!");
        }
        reservationService.cancelReservation(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete a Reservation", description = "Deletes a reservation based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Reservation doesn't exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "204", description = "Successful deletion of flight", content = @Content)
    })
    @DeleteMapping("/{id:^[0-9]+$}")
    public ResponseEntity<HttpStatus> delete(@PathVariable long id) {
        reservationService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
