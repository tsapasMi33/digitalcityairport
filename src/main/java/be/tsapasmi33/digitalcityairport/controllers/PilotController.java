package be.tsapasmi33.digitalcityairport.controllers;

import be.tsapasmi33.digitalcityairport.exceptions.ErrorResponse;
import be.tsapasmi33.digitalcityairport.models.dto.PilotDTO;
import be.tsapasmi33.digitalcityairport.models.entities.Pilot;
import be.tsapasmi33.digitalcityairport.models.form.PilotForm;
import be.tsapasmi33.digitalcityairport.services.PilotService;
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

@Tag(name = "Pilot Controller", description = "Manage pilots")
@AllArgsConstructor
@RestController
@RequestMapping("/pilot")
public class PilotController {

    private final PilotService pilotService;

    @Operation(summary = "Retrieves Pilots", description = "Provides a list of all pilots")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of pilots",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PilotDTO.class))))
    @GetMapping("/all")
    public ResponseEntity<List<PilotDTO>> getAll() {
        return ResponseEntity.ok(
                pilotService.getAll().stream()
                        .map(PilotDTO::toDto)
                        .toList()
        );
    }

    @Operation(summary = "Get Pilot by Id", description = "Returns a pilot based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Pilot doesn't exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful retrieval of pilot", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PilotDTO.class)))
    })
    @GetMapping("/{id:^[0-9]+$}")
    public ResponseEntity<PilotDTO> getOne(@PathVariable long id) {
        return ResponseEntity.ok(
                PilotDTO.toDto(pilotService.getOne(id))
        );
    }

    @Operation(summary = "Create a Pilot", description = "Creates a pilot from the provided payload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful submission", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "201", description = "Successful creation of a pilot", content = @Content)
    })
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody PilotForm form) {
        pilotService.insert(form.toEntity());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @Operation(summary = "Update Pilot by Id", description = "Updates a pilot based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Pilot doesn't exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful update of pilot", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PilotDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful submission", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id:^[0-9]+$}")
    public ResponseEntity<PilotDTO> update(@PathVariable long id, @RequestBody PilotForm form) {
        Pilot updated = pilotService.update(id, form.toEntity());

        return ResponseEntity.ok(PilotDTO.toDto(updated));
    }

    @Operation(summary = "Gives a licence to Pilot", description = "Gives a licence to fly a certain airplane to a pilot based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Pilot or airplane type doesn't exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "204", description = "Successful licencing of pilot", content = @Content)
    })
    @PatchMapping(path = "/{id:^[0-9]+$}", params = "airplaneTypeId")
    public ResponseEntity<HttpStatus> giveLicence(@PathVariable long id, @RequestParam long airplaneTypeId) {
        pilotService.addLicence(id, airplaneTypeId);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete a Pilot", description = "Deletes a pilot based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Pilot doesn't exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "204", description = "Successful deletion of pilot", content = @Content)
    })
    @DeleteMapping("/{id:^[0-9]+$}")
    public ResponseEntity<HttpStatus> delete(@PathVariable long id) {
        pilotService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
