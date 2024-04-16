package io.kawoolutions.bbstats.rest;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.kawoolutions.bbstats.entity.Person;
import io.kawoolutions.bbstats.repository.PersonRepository;

@RestController
@RequestMapping(value = "/person", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonResource {

    @Autowired
    private Logger log;

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("findall")
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @GetMapping("insert")
    public void insert() {
//        this.personRepository.saveAll(this.testCities);
    }
}
