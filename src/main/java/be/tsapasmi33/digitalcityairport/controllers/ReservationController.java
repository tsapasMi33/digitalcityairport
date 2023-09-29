package be.tsapasmi33.digitalcityairport.controllers;

import be.tsapasmi33.digitalcityairport.models.dto.ReservationDTO;
import be.tsapasmi33.digitalcityairport.models.entities.Reservation;
import be.tsapasmi33.digitalcityairport.models.form.ReservationForm;
import be.tsapasmi33.digitalcityairport.services.FlightService;
import be.tsapasmi33.digitalcityairport.services.PassengerService;
import be.tsapasmi33.digitalcityairport.services.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final FlightService flightService;
    private final PassengerService passengerService;

    @GetMapping(path = "/all", params = {"reservationDate", "cancelled", "flightId", "passengerId"})
    public ResponseEntity<List<ReservationDTO>> getAll(@RequestParam(required = false) LocalDate reservationDate,
                                                       @RequestParam(required = false) Boolean cancelled,
                                                       @RequestParam(required = false) Long flightId,
                                                       @RequestParam(required = false) Long passengerId) {

        return ResponseEntity.ok(
                reservationService.findAllByCriteria(reservationDate, cancelled, flightId, passengerId)
                        .stream()
                        .map(ReservationDTO::toDto)
                        .toList()
        );
    }

    @GetMapping("/{id:^[0-9]+$}")
    public ResponseEntity<ReservationDTO> getOne(@RequestParam long id) {
        return ResponseEntity.ok(ReservationDTO.toDto(reservationService.getOne(id)));
    }

    @PostMapping(path = "/create", params = {"price", "flightId", "passengerId"})
    public ResponseEntity<HttpStatus> create(@RequestBody ReservationForm form, @RequestParam double price, @RequestParam long flightId, @RequestParam long passengerId) {
        Reservation reservation = form.toEntity();
        reservation.setPrice(price);
        reservation.setFlight(flightService.getOne(flightId));
        reservation.setPassenger(passengerService.getOne(passengerId));

        reservationService.insert(reservation);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id:^[0-9]+$}")
    public ResponseEntity<ReservationDTO> update(@PathVariable long id, @RequestBody ReservationForm form) {
        Reservation updated = reservationService.update(id, form.toEntity());
        return ResponseEntity.ok(ReservationDTO.toDto(updated));
    }

    @PatchMapping("/{id:^[0-9]+$}/cancel")
    public ResponseEntity<HttpStatus> cancel(@PathVariable long id) {
        reservationService.cancelReservation(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id:^[0-9]+$}")
    public ResponseEntity<HttpStatus> delete(@PathVariable long id) {
        reservationService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
