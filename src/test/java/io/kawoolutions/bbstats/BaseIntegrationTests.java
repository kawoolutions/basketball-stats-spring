package io.kawoolutions.bbstats;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
public abstract class BaseIntegrationTests {

    @Autowired
    protected Logger log;
}