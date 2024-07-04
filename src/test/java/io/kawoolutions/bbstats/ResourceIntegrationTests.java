package io.kawoolutions.bbstats;

import java.util.List;

import io.kawoolutions.bbstats.rest.PersonResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.kawoolutions.bbstats.entity.Person;
import io.kawoolutions.bbstats.entity.PersonGender;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ResourceIntegrationTests {

    @Autowired
    private PersonResource personResource;

    @Test
    void contextLoads() {
        // if this test is OK, then the SQL scripts schema.sql and data.sql were executed successfully
    }

    @Test
    void shouldInsertMultiplePersons() {
        List<Person> personsBefore = personResource.getAllPersons();
        int sizeBefore = personsBefore.size();

        List<Person> personsToInsert = List.of(
                new Person("Fabian", "Herrmann", PersonGender.MALE),
                new Person("Annamarie", "Kuhn", PersonGender.FEMALE),
                new Person("Lukas", "Mayer", PersonGender.MALE),
                new Person("Patrizia", "Flechtner", PersonGender.FEMALE)
        );

        personsToInsert.forEach(p -> personResource.createPerson(p));

        List<Person> personsAfter = personResource.getAllPersons();
        int sizeAfter = personsAfter.size();

        assertThat(sizeAfter - sizeBefore).isEqualTo(personsToInsert.size());
    }
}
