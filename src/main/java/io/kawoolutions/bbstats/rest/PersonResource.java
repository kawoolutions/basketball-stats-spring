package io.kawoolutions.bbstats.rest;

import java.net.URI;
import java.util.List;
import java.util.Objects;

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

import io.kawoolutions.bbstats.entity.Person;
import io.kawoolutions.bbstats.repository.PersonRepository;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

//@CrossOrigin
@RestController
@RequestMapping(value = "/persons")
public class PersonResource {

    @Autowired
    private Logger log;

    @Autowired
    private PersonRepository personRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Person getOnePerson(@PathVariable Integer id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> createPerson(@RequestBody Person person, UriComponentsBuilder uriComponentsBuilder) {
        if (Objects.nonNull(person.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID of new person must not be set");
        }

        Person saved = personRepository.save(person);

        URI uri = MvcUriComponentsBuilder.fromController(getClass())
            .path("/{id}")
            .buildAndExpand(saved.getId())
            .toUri();

        return ResponseEntity.created(uri).body(saved);

//        URI uri = uriComponentsBuilder
//            .pathSegment("persons", person.getId().toString())
//            .build().toUri();
//
//        return ResponseEntity.created(uri).build();
    }

    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updatePerson(@PathVariable Integer id, @RequestBody Person person) {
        if (personRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        personRepository.save(person);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Integer id) {
        if (!personRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        personRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
