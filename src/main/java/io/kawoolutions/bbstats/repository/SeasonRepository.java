package io.kawoolutions.bbstats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.kawoolutions.bbstats.entity.Season;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Integer> {

    Season findByStartYear(Integer startYear);
}
