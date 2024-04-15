package io.kawoolutions.bbstats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.kawoolutions.bbstats.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
}
