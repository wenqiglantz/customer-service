package com.github.wenqiglantz.service.customerservice.persistence.repository;

import com.github.wenqiglantz.service.customerservice.persistence.entity.Customer;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@DataJpaTest
public class CustomerRepositoryTest {
    private static final String CUSTOMER_ID = "ABCDEFG12345678910HIJKLMNOP12345";
    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String LAST_NAME = "LAST_NAME";

    private static final Customer CUSTOMER = Customer.builder()
            .customerId(CUSTOMER_ID)
            .firstName(FIRST_NAME)
            .lastName(LAST_NAME)
            .build();

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    public void cleanup() {
        customerRepository.deleteAll();
    }

    @Test
    public void findByCustomerId() {
        customerRepository.save(CUSTOMER);
        Optional<Customer> newCustomer = customerRepository.findByCustomerId(CUSTOMER_ID);
        MatcherAssert.assertThat(newCustomer.isPresent(), Matchers.is(Matchers.equalTo(true)));
        MatcherAssert.assertThat(newCustomer.get(), Matchers.is(Matchers.equalTo(CUSTOMER)));
    }

    @Test
    public void findByCustomerIdReturnsNoResults() {
        Optional<Customer> newCustomer = customerRepository.findByCustomerId("differentId");
        MatcherAssert.assertThat(newCustomer.isPresent(), Matchers.is(Matchers.equalTo(false)));
    }
}
