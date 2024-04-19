package io.kawoolutions.bbstats.rest;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.kawoolutions.bbstats.entity.Season;
import io.kawoolutions.bbstats.repository.SeasonRepository;

@RestController
@RequestMapping(value = "/season", produces = MediaType.APPLICATION_JSON_VALUE)
public class SeasonResource {

    @Autowired
    private Logger log;

    @Autowired
    private SeasonRepository seasonRepository;

    @GetMapping("findall")
    public List<Season> findAll() {
        return seasonRepository.findAll();
    }

    @GetMapping("finddefault")
    public Season findDefault() {
        return seasonRepository.findByStartYear(2019);
    }

    @GetMapping("insert")
    public void insert() {
//        this.seasonRepository.saveAll(this.testCities);
    }
}
