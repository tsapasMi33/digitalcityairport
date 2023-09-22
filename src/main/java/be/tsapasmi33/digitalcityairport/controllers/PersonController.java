package be.tsapasmi33.digitalcityairport.controllers;

import be.tsapasmi33.digitalcityairport.models.entities.Person;
import be.tsapasmi33.digitalcityairport.services.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/people")
public class PersonController {

    PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Person>> getAll(){
        System.out.println("ok");
        return ResponseEntity.ok()
                .body(personService.getPeople());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getById(@PathVariable String id) {
        Person person = personService.getPerson(id);
        if (person != null) {
            return ResponseEntity.ok()
                    .body(person);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/all", params = "name" )
    public ResponseEntity<List<Person>> getByNameLike(@RequestParam String name) {
        return ResponseEntity.ok()
                .body(personService.getPersonIfNameLike(name));
    }

    @PostMapping("/add")
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {

    }

}
