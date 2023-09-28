package be.tsapasmi33.digitalcityairport.controllers;

import be.tsapasmi33.digitalcityairport.models.dto.PersonDTO;
import be.tsapasmi33.digitalcityairport.models.entities.FidelityStatus;
import be.tsapasmi33.digitalcityairport.models.entities.Person;
import be.tsapasmi33.digitalcityairport.models.form.PersonForm;
import be.tsapasmi33.digitalcityairport.services.PersonService;
import be.tsapasmi33.digitalcityairport.services.impl.PersonServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    // http://localhost:8080/person
    // http://localhost:8080/person/all
    // http://localhost:8080/person/all?containing=truc
    @GetMapping(path = {"", "/all"}/*, params = "containing"*/)
    public ResponseEntity<List<PersonDTO>> getAll(@RequestParam(required = false) String containing){
        List<Person> personList;

        if( containing == null || containing.trim().isEmpty() )
            personList =  personService.getAll();
        else
            personList = personService.getWithNameContaining(containing);

        return ResponseEntity.ok(
                personList.stream()
                        .map(PersonDTO::toDto)
                        .toList()
        );
    }

    // GET - http://localhost:8080/person/1
    @GetMapping("/{id:^[0-9]+$}")
    public ResponseEntity<PersonDTO> getOne(@PathVariable long id){
        return ResponseEntity.ok(
                PersonDTO.toDto( personService.getOne(id) )
        );
    }

    // POST - http://localhost:8080/person
    // POST - http://localhost:8080/person/add
    @PostMapping({"","/add"})
    public ResponseEntity<?> add(@RequestBody PersonForm form){
        personService.insert( form.toEntity() );

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    // PUT - http://localhost:8080/person/1
    @PutMapping("/{id:^[0-9]+$}")
    public ResponseEntity<PersonDTO> update(@PathVariable long id, @RequestBody PersonForm form){
        Person updated = personService.update(id, form.toEntity());
        return ResponseEntity.ok(PersonDTO.toDto(updated));
    }

    // PATCH - http://localhost:8080/person/1?fidelity=NONE
    @PatchMapping(value = "/{id:^[0-9]+$}", params = "fidelity")
    public ResponseEntity<?> updateFidelity(@PathVariable long id, @RequestParam FidelityStatus fidelity){
        personService.updateFidelity(id, fidelity);

        return ResponseEntity.noContent()
                .build();
    }

    @DeleteMapping("/{id:^[0-9]+$}")
    public ResponseEntity<?> delete(@PathVariable long id){
        personService.delete(id);

        return ResponseEntity.noContent()
                .build();
    }

}