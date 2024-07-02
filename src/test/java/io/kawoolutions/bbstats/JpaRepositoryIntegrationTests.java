package io.kawoolutions.bbstats;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.kawoolutions.bbstats.entity.Continent;
import io.kawoolutions.bbstats.entity.Season;
import io.kawoolutions.bbstats.repository.ContinentRepository;
import io.kawoolutions.bbstats.repository.SeasonRepository;

@SpringBootTest
class JpaRepositoryIntegrationTests {

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private ContinentRepository continentRepository;

    @Test
    void contextLoads() {
        // if this test is OK, then the SQL scripts schema.sql and data.sql were executed successfully
    }

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
