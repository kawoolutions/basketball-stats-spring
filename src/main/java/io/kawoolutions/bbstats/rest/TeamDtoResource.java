package io.kawoolutions.bbstats.rest;

import io.kawoolutions.bbstats.dto.TeamDto;
import io.kawoolutions.bbstats.entity.Person;
import io.kawoolutions.bbstats.repository.TeamDtoRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

    @RestController
    @RequestMapping(value = "/teamdto", produces = MediaType.APPLICATION_JSON_VALUE)
    public class TeamDtoResource {

        @Autowired
        private TeamDtoRepository teamDtoRepository;

        @GetMapping("/findall/{seasonStartYear}")
        public List<TeamDto> findBySeasonStartYear(Integer seasonStartYear) {
            return teamDtoRepository.findBySeasonStartYear(seasonStartYear);
        }
    }
