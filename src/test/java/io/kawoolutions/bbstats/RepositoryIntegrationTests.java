package io.kawoolutions.bbstats;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import io.kawoolutions.bbstats.entity.Person;
import io.kawoolutions.bbstats.entity.PersonGender;
import io.kawoolutions.bbstats.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.kawoolutions.bbstats.entity.Continent;
import io.kawoolutions.bbstats.entity.Season;
import io.kawoolutions.bbstats.repository.ContinentRepository;
import io.kawoolutions.bbstats.repository.SeasonRepository;

@SpringBootTest
class RepositoryIntegrationTests {

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private ContinentRepository continentRepository;

    @Autowired
    private PersonRepository personRepository;

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

    @Test
    void shouldInsertMultiplePersons() {
        List<Person> personsBefore = personRepository.findAll();
        int sizeBefore = personsBefore.size();

        List<Person> personsToInsert = List.of(
                new Person("Fabian", "Müller", PersonGender.MALE),
                new Person("Anna Marie", "Kuhn", PersonGender.FEMALE),
                new Person("Lukas", "Füllkrug", PersonGender.MALE),
                new Person("Patrizia", "Flechtner", PersonGender.FEMALE)
        );

        personRepository.saveAll(personsToInsert);

        List<Person> personsAfter = personRepository.findAll();
        int sizeAfter = personsAfter.size();

        assertThat(sizeAfter - sizeBefore).isEqualTo(personsToInsert.size());
    }
}
