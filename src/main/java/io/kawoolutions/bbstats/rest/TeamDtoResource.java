package io.kawoolutions.bbstats.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.kawoolutions.bbstats.dto.TeamDto;
import io.kawoolutions.bbstats.repository.TeamDtoRepository;

@RestController
@RequestMapping(value = "/teamdto", produces = MediaType.APPLICATION_JSON_VALUE)
public class TeamDtoResource {

    @Autowired
    private TeamDtoRepository teamDtoRepository;

    @GetMapping("/findall/{seasonStartYear}")
    public List<TeamDto> findBySeasonStartYear(@PathVariable Integer seasonStartYear) {
        return teamDtoRepository.findBySeasonStartYear(seasonStartYear);
    }
}
