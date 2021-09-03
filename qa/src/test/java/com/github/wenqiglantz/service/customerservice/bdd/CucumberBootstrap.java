package com.github.wenqiglantz.service.customerservice.bdd;

import com.github.wenqiglantz.service.customerservice.persistence.repository.CustomerRepository;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberBootstrap {

    @Autowired
    private CustomerRepository customerRepository;

    //Method annotated with @After executes after every scenario
    @After
    public void cleanUp() {
        log.info(">>> cleaning up after scenario!");
        customerRepository.deleteAll();
    }

    //Method annotated with @AfterStep executes after every step
    @AfterStep
    public void afterStep() {
        log.info(">>> AfterStep!");
        //placeholder for after step logic
    }

    //Method annotated with @Before executes before every scenario
    @Before
    public void before() {
        log.info(">>> Before scenario!");
        //placeholder for before scenario logic
    }

    //Method annotated with @BeforeStep executes before every step
    @BeforeStep
    public void beforeStep() {
        log.info(">>> BeforeStep!");
        //placeholder for before step logic
    }
}
