package io.kawoolutions.bbstats.repository;

import io.kawoolutions.bbstats.entity.Season;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Integer> {

    List<Season> findAll();

    Season findByStartYear(Integer startYear);
}
