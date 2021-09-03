package com.github.wenqiglantz.service.customerservice.bdd.steps;

import com.github.wenqiglantz.service.customerservice.bdd.HttpClient;
import com.github.wenqiglantz.service.customerservice.data.CustomerInfo;
import com.github.wenqiglantz.service.customerservice.persistence.entity.Customer;
import com.github.wenqiglantz.service.customerservice.persistence.repository.CustomerRepository;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@Slf4j
public class CustomerSteps {

    @Autowired
    private HttpClient httpClient;

    @Autowired
    private CustomerRepository customerRepository;

    @When("^CustomerInfo with the following inputs is passed into createCustomer endpoint:$")
    public void customer_info_passed_in(CustomerInfo customerInfo) {
        log.info("Passing customerInfo {} to createCustomer endpoint " + customerInfo);
        int statusCode = httpClient.post(customerInfo);
        assertThat(statusCode, is(equalTo(HttpStatus.CREATED.value())));
    }

    @Then("^A new customer is created$")
    public void new_customer_created() {
        List<Customer> customers = customerRepository.findAll();
        Customer customer = customers.get(0);
        assertThat(customer.getFirstName(), is(equalTo("first name")));
        assertThat(customer.getLastName(), is(equalTo("last name")));
    }
}
