package io.kawoolutions.bbstats.rest;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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

import io.kawoolutions.bbstats.entity.Coach;
import io.kawoolutions.bbstats.entity.Person;
import io.kawoolutions.bbstats.entity.Player;
import io.kawoolutions.bbstats.entity.Referee;
import io.kawoolutions.bbstats.repository.CoachRepository;
import io.kawoolutions.bbstats.repository.PlayerRepository;
import io.kawoolutions.bbstats.repository.RefereeRepository;
import io.kawoolutions.bbstats.repository.PersonRepository;

//@CrossOrigin
@RestController
@RequestMapping(value = "/persons")
public class PersonResource {

    @Autowired
    private Logger log;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private RefereeRepository refereeRepository;

    @Operation(summary = "Get all persons")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Operation(summary = "Get a person")
    @ApiResponse(description = "found person")
    @ApiResponse(responseCode = "404", description = "person not found")
    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Person getOnePerson(@PathVariable Integer id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Create a person")
    @ApiResponse(responseCode = "400", description = "ID of person must not be pre-set")
    @Transactional
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        if (Objects.nonNull(person.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID of new person must not be set");
        }

        Optional<Player> player = Optional.ofNullable(person.getPlayer());
        Optional<Coach> coach = Optional.ofNullable(person.getCoach());
        Optional<Referee> referee = Optional.ofNullable(person.getReferee());

        // set relationships to null because cascade type is ALL and for create the AUTO ID isn't know yet (which is shared by these role entities)
        // so in effect creating a person with related, auto-ID sharing entities must take a manual extra step for each relationship
        person.setPlayer(null);
        person.setCoach(null);
        person.setReferee(null);
//        person.setUser(null); // doesn't cascade, see entity mappings

        Person savedPerson = personRepository.save(person);
        Integer sharedId = savedPerson.getId();

        player.ifPresent(p -> {
            p.setId(sharedId);
            savedPerson.setPlayer(playerRepository.save(p));
        });
        coach.ifPresent(c -> {
            c.setId(sharedId);
            savedPerson.setCoach(coachRepository.save(c));
        });
        referee.ifPresent(r -> {
            r.setId(sharedId);
            savedPerson.setReferee(refereeRepository.save(r));
        });

        URI uri = MvcUriComponentsBuilder.fromController(getClass())
            .path("/{id}")
            .buildAndExpand(sharedId)
            .toUri();

        return ResponseEntity.created(uri).body(savedPerson);
    }

    @Operation(summary = "Update a person")
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

    @Operation(summary = "Delete a person")
    @ApiResponse(responseCode = "404", description = "person not found")
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Integer id) {
        if (!personRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        personRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
