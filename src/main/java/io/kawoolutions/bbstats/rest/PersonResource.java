package io.kawoolutions.bbstats.rest;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.kawoolutions.bbstats.entity.Person;
import io.kawoolutions.bbstats.repository.PersonRepository;

//@CrossOrigin
@RestController
@RequestMapping(value = "/person-management")
public class PersonResource {

    @Autowired
    private Logger log;

    @Autowired
    private PersonRepository personRepository;

    @GetMapping(name = "persons", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> getAll() {
        return personRepository.findAll();
    }

    @PostMapping(name = "persons", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createPerson(@RequestBody Person person) {
        personRepository.save(person);
    }
}
