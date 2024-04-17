package io.kawoolutions.bbstats.repository;

import io.kawoolutions.bbstats.dto.TeamDto;
import io.kawoolutions.bbstats.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamDtoRepository extends JpaRepository<TeamDto, Integer> {

    @Query("""
              SELECT NEW io.kawoolutions.bbstats.dto.TeamDto(
              ro.id,
              cl.name,
              cl.code,
              te.ordinalNbr,
              tt.code,
              tt.ageGroup,
              tt.gender,
              ct,
              cy,
              re,
              st,
              di
            )
            FROM Roster ro
              JOIN ro.season se
              JOIN ro.team te
              JOIN te.teamType tt
              JOIN te.club cl
              JOIN cl.district di
              JOIN di.parent st
              JOIN st.parent re
              JOIN re.parent cy
              JOIN cy.parent ct
            WHERE se.startYear = :seasonStartYear
            ORDER BY ct.name, cy.name, re.name, st.name, di.name, cl.code, tt.ageGroup DESC, tt.gender DESC, te.ordinalNbr
    """)
    List<TeamDto> findBySeasonStartYear(Integer seasonStartYear);
}
