package io.kawoolutions.bbstats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.kawoolutions.bbstats.entity.Referee;

@Repository
public interface RefereeRepository extends JpaRepository<Referee, Integer> {
}
