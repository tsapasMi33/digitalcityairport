package be.tsapasmi33.digitalcityairport.controllers;

import be.tsapasmi33.digitalcityairport.models.entities.Person;
import be.tsapasmi33.digitalcityairport.services.PersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.AllArgsConstructor;
import org.springframework.data.web.JsonPath;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/people")
public class PersonController {

    PersonService personService;
    ObjectMapper objectMapper;

    @GetMapping("/all")
    public ResponseEntity<List<Person>> getAll(){
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
        personService.addPerson(person);
        return ResponseEntity.created(URI.create("/people/"+person.getId()))
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePerson(@PathVariable String id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> modifyPerson(@PathVariable String id, @RequestBody Person person) {
        return ResponseEntity
                .accepted()
                .body(personService.updatePerson(id, person));
    }

    @PatchMapping(value = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<Person> updateFidelity(@PathVariable String id, @RequestBody JsonPatch patch) {
        try {
            Person person = personService.getPerson(id);
            Person patchedPerson = applyPatchToPerson(patch, person);
            personService.updatePerson(id, patchedPerson);
            return ResponseEntity.ok()
                    .body(patchedPerson);
        } catch (JsonPatchException | JsonProcessingException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    private Person applyPatchToPerson(JsonPatch patch, Person targetPerson) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetPerson, JsonNode.class));
        return objectMapper.treeToValue(patched, Person.class);
    }

//    {
//        "op":"replace",
//            "path":"/status",
//            "value":"GOLD"
//    }
}
