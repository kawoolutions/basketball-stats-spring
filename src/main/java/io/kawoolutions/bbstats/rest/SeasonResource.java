package io.kawoolutions.bbstats.rest;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.kawoolutions.bbstats.entity.Season;
import io.kawoolutions.bbstats.repository.SeasonRepository;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/seasons")
public class SeasonResource {

    @Autowired
    private Logger log;

    @Autowired
    private SeasonRepository seasonRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Season> getAllSeasons() {
        return seasonRepository.findAll();
    }

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Season getOneSeason(@PathVariable Integer id) {
        return seasonRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "default", produces = MediaType.APPLICATION_JSON_VALUE)
    public Season getDefaultSeason() {
        return seasonRepository.findByStartYear(2019);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createSeason(@RequestBody Season season) {
//        if (Objects.nonNull(season.getId())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID of new entry must not be set");
//        }
        seasonRepository.save(season);
    }

//    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public void updateSeason(@PathVariable Integer id, @RequestBody Season season) {
//        if (seasonRepository.findById(id).isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        }
//        seasonRepository.save(season);
//    }

    @DeleteMapping(path = "{id}")
    public void deleteSeason(@PathVariable Integer id) {
        seasonRepository.deleteById(id);
    }
}
