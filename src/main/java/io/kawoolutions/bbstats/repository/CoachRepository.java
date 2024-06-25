package io.kawoolutions.bbstats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.kawoolutions.bbstats.entity.Coach;

@Repository
public interface CoachRepository extends JpaRepository<Coach, Integer> {
}
