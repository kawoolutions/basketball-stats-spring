package io.kawoolutions.bbstats.rest;

import io.kawoolutions.bbstats.entity.Person;
import io.kawoolutions.bbstats.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "demo/basics/persons", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonResource {

    private static Logger log = LoggerFactory.getLogger(PersonResource.class);

    @Autowired
    private PersonRepository personRepository;


    @GetMapping("findAll")
    public List<Person> findAll() {
        log.info("----- findAll -----");

        List<Person> persons = personRepository.findAllWithFetchGraph(Person.FETCH_EMAIL_ADDRESSES_PHONE_NUMBERS_AND_ROLES);

        return persons;
    }

    @GetMapping("insert")
    public void insert() {
        this.personRepository.saveAll(this.testCities);
    }
}
