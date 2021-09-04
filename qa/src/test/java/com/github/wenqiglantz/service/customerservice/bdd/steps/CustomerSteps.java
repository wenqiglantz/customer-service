package com.github.wenqiglantz.service.customerservice.bdd.steps;

import com.github.wenqiglantz.service.customerservice.bdd.HttpClient;
import com.github.wenqiglantz.service.customerservice.data.CustomerInfo;
import com.github.wenqiglantz.service.customerservice.persistence.entity.Customer;
import com.github.wenqiglantz.service.customerservice.persistence.repository.CustomerRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@Slf4j
public class CustomerSteps {

    @Autowired
    private HttpClient httpClient;

    @Autowired
    private CustomerRepository customerRepository;

    @Given("^the collection of customers:$")
    public void collection_of_customers(DataTable dataTable) {
        dataTable.asList(CustomerInfo.class).forEach(customerInfo -> {
            saveCustomer((CustomerInfo)customerInfo);
        });
    }

    @When("^customerId (.+) is passed in to retrieve the customer details$")
    public void get_customer_details_by_id(String customerId) {
        CustomerInfo customerInfo = httpClient.getCustomer(customerId);
        assertThat(customerInfo, is(notNullValue()));
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
