package io.kawoolutions.bbstats;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.kawoolutions.bbstats.entity.Continent;
import io.kawoolutions.bbstats.entity.Season;
import io.kawoolutions.bbstats.repository.ContinentRepository;
import io.kawoolutions.bbstats.repository.SeasonRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DataJpaRepositoryTests {

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private ContinentRepository continentRepository;

    @Test
    void testDatabaseSeasons() {
        // given
        List<Season> seasonsBefore = seasonRepository.findAll();

        // when
//        List<Season> seasonsAfter = seasonRepository.findAll();

        // then
        assertEquals(seasonsBefore.size(), 5);
    }

    @Test
    void testDatabaseContinents() {
        List<Continent> continents = continentRepository.findAll();
        assertEquals(continents.size(), 7);
    }
}
