package io.kawoolutions.bbstats;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql({"/schema.sql", "/data.sql"})
class BasketballStatsSpringApplicationTests {

    @Test
    void contextLoads() {
    }

}
