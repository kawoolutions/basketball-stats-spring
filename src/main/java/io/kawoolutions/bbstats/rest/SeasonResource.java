package io.kawoolutions.bbstats.rest;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.kawoolutions.bbstats.entity.Season;
import io.kawoolutions.bbstats.repository.SeasonRepository;

@RestController
@RequestMapping(value = "/season-management", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class SeasonResource {

    @Autowired
    private Logger log;

    @Autowired
    private SeasonRepository seasonRepository;

    @GetMapping("seasons")
    public List<Season> getAllSeasons() {
        return seasonRepository.findAll();
    }

    @GetMapping("seasons/default")
    public Season getDefaultSeason() {
        return seasonRepository.findByStartYear(2019);
    }

    @PostMapping("seasons")
    public void createSeason(@RequestBody Season season) {
        seasonRepository.save(season);
    }

    @PutMapping("seasons")
    public void updateSeason(@RequestBody Season season) {
        seasonRepository.save(season);
    }

    @DeleteMapping("seasons/{seasonId}")
    public void deleteSeason(@PathVariable Integer seasonId) {
        seasonRepository.deleteById(seasonId);
    }
}
