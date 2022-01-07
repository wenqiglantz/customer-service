package com.github.wenqiglantz.service.customerservice.bdd.steps;

import com.github.wenqiglantz.service.customerservice.bdd.CucumberBootstrap;
import com.github.wenqiglantz.service.customerservice.data.CustomerInfo;
import com.github.wenqiglantz.service.customerservice.persistence.entity.Customer;
import com.github.wenqiglantz.service.customerservice.persistence.repository.CustomerRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@Slf4j
public class CustomerSteps extends CucumberBootstrap {

    @Autowired
    private CustomerRepository customerRepository;

    //this method executes after every scenario
    @After
    public void cleanUp() {
        log.info(">>> cleaning up after scenario!");
        customerRepository.deleteAll();
    }

    //this method executes after every step
    @AfterStep
    public void afterStep() {
        log.info(">>> AfterStep!");
        //placeholder for after step logic
    }

    //this method executes before every scenario
    @Before
    public void before() {
        log.info(">>> Before scenario!");
        //placeholder for before scenario logic
    }

    //this method executes before every step
    @BeforeStep
    public void beforeStep() {
        log.info(">>> BeforeStep!");
        //placeholder for before step logic
    }

    @Given("^the collection of customers:$")
    public void collection_of_customers(DataTable dataTable) {
        dataTable.asList(CustomerInfo.class).forEach(customerInfo -> {
            saveCustomer((CustomerInfo)customerInfo);
        });
    }

    @When("^customerId (.+) is passed in to retrieve the customer details$")
    public void get_customer_details_by_id(String customerId) {
        ResponseEntity<CustomerInfo> response = testRestTemplate.getForEntity(
                "/customers/" + customerId, CustomerInfo.class, customerId);
        assertThat(response.getBody(), is(notNullValue()));
        assertThat(response.getBody().getCustomerId(), is(equalTo(customerId)));
    }

    @Then("^The customer detail is retrieved$")
    public void customer_detail_retrieved(DataTable dataTable) {
        dataTable.asList(CustomerInfo.class).forEach(customerInfo -> {
            Optional<Customer> customerOptional =
                    customerRepository.findByCustomerId(((CustomerInfo)customerInfo).getCustomerId());
            if (customerOptional.isPresent()){
                assertThat(customerOptional.get().getFirstName(), is(equalTo(((CustomerInfo)customerInfo).getFirstName())));
                assertThat(customerOptional.get().getLastName(), is(equalTo(((CustomerInfo)customerInfo).getLastName())));
            }
        });
    }

    private void saveCustomer(CustomerInfo customerInfo) {
        customerRepository.save(Customer.builder()
                .customerId(customerInfo.getCustomerId())
                .firstName(customerInfo.getFirstName())
                .lastName(customerInfo.getLastName())
                .build());
    }
}
