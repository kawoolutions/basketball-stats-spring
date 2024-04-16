package io.kawoolutions.bbstats.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.kawoolutions.bbstats.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    @EntityGraph(value = Person.FETCH_EMAIL_ADDRESSES_PHONE_NUMBERS_AND_ROLES)
    List<Person> findAll();
}
