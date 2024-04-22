package io.kawoolutions.bbstats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.kawoolutions.bbstats.entity.Continent;

@Repository
public interface ContinentRepository extends JpaRepository<Continent, Integer> {
}