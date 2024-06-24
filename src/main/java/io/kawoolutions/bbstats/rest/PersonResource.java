package io.kawoolutions.bbstats.rest;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import io.kawoolutions.bbstats.entity.Person;
import io.kawoolutions.bbstats.repository.PersonRepository;

//@CrossOrigin
@RestController
@RequestMapping(value = "/persons")
public class PersonResource {

    @Autowired
    private Logger log;

    @Autowired
    private PersonRepository personRepository;

    @Operation(summary = "get all persons")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Operation(summary = "get a person")
    @ApiResponse(description = "found person")
    @ApiResponse(responseCode = "404", description = "person not found")
    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Person getOnePerson(@PathVariable Integer id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "create new person")
    @ApiResponse(responseCode = "400", description = "ID of person must not be pre-set")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        if (Objects.nonNull(person.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID of new person must not be set");
        }

        Person saved = personRepository.save(person);

        URI uri = MvcUriComponentsBuilder.fromController(getClass())
            .path("/{id}")
            .buildAndExpand(saved.getId())
            .toUri();

        return ResponseEntity.created(uri).body(saved);
    }

    @Operation(summary = "update person")
    @ApiResponse(responseCode = "400", description = "ID of person must not be changed")
    @ApiResponse(responseCode = "404", description = "person not found")
    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> updatePerson(@PathVariable Integer id, @RequestBody Person person) {
        if (!Objects.equals(id, person.getId())) {
            return ResponseEntity.badRequest()
                .header("x-message","ID of updated person must correspond to path")
                .build();
        }

        if (!personRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Person saved = personRepository.save(person);
        return ResponseEntity.ok(saved);
    }

    @Operation(summary = "delete a person")
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Integer id) {
        if (!personRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        personRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
