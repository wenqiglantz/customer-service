package com.github.wenqiglantz.service.customerservice.service;

import com.github.wenqiglantz.service.customerservice.data.CustomerInfo;
import com.github.wenqiglantz.service.customerservice.data.exception.NotFoundException;
import com.github.wenqiglantz.service.customerservice.persistence.entity.Customer;
import com.github.wenqiglantz.service.customerservice.persistence.repository.CustomerRepository;
import com.github.wenqiglantz.service.customerservice.service.impl.CustomerServiceImpl;
import io.dapr.exceptions.DaprException;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

public class CustomerServiceTest {
    private static final String CUSTOMER_ID = "ABCDEFG12345678910HIJKLMNOP12345";
    private static final String FIRST_NAME = "FIRST";
    private static final String LAST_NAME = "LAST";

    private static final CustomerInfo API_CUSTOMER = CustomerInfo.builder()
            .customerId(CUSTOMER_ID)
            .firstName(FIRST_NAME)
            .lastName(LAST_NAME)
            .build();

    private static final CustomerInfo API_CUSTOMER_NULL_ID = CustomerInfo.builder()
            .firstName(FIRST_NAME)
            .lastName(LAST_NAME)
            .build();

    private static final Customer CUSTOMER = Customer.builder()
            .customerId(CUSTOMER_ID)
            .firstName(FIRST_NAME)
            .lastName(LAST_NAME)
            .build();

    private CustomerRepository customerRepository;

    @RegisterExtension
    Mockery context = new JUnit5Mockery();

    @BeforeEach
    public void setUp() {
        customerRepository = context.mock(CustomerRepository.class);
    }

    @Test
    public void saveCustomer() throws Exception {
        context.checking(new Expectations() {
            {
                oneOf(customerRepository).save(with(any(Customer.class)));
            }
        });

        CustomerService customerService = new CustomerServiceImpl(customerRepository);
        //Dapr pub/sub testing is handled by Pact testing
        Assertions.assertThrows(DaprException.class, () -> {
            customerService.saveCustomer(API_CUSTOMER);
        });
    }

    @Test
    public void saveCustomerWithCustomerIdNull() throws Exception {
        context.checking(new Expectations() {
            {
                oneOf(customerRepository).save(with(any(Customer.class)));
            }
        });

        CustomerService customerService = new CustomerServiceImpl(customerRepository);
        //Dapr pub/sub testing is handled by Pact testing
        Assertions.assertThrows(DaprException.class, () -> {
            customerService.saveCustomer(API_CUSTOMER_NULL_ID);
        });
    }

    @Test
    public void getCustomers() {
        Pageable pageable = PageRequest.of(0, 20, Sort.by("firstName"));
        context.checking(new Expectations() {
            {
                oneOf(customerRepository).findAll(pageable);
                will(returnValue(new PageImpl<>(Collections.singletonList(CUSTOMER))));
            }
        });

        CustomerService customerService = new CustomerServiceImpl(customerRepository);
        Page<CustomerInfo> customerInfos = customerService.getCustomers(pageable);
        assertThat(customerInfos.getTotalElements(), is(equalTo(1L)));
    }

    @Test
    public void getCustomer() {
        context.checking(new Expectations() {
            {
                oneOf(customerRepository).findByCustomerId(CUSTOMER_ID);
                will(returnValue(Optional.of(CUSTOMER)));
            }
        });

        CustomerService customerService = new CustomerServiceImpl(customerRepository);
        CustomerInfo customerInfo = customerService.getCustomer(CUSTOMER_ID);
        assertThat(customerInfo.getFirstName(), is(equalTo(CUSTOMER.getFirstName())));
    }

    @Test
    public void getCustomerReturnsEmptyResults() {
        context.checking(new Expectations() {
            {
                oneOf(customerRepository).findByCustomerId("test");
                will(returnValue(Optional.empty()));
            }
        });

        CustomerService customerService = new CustomerServiceImpl(customerRepository);
        Assertions.assertThrows(NotFoundException.class, () -> {
            customerService.getCustomer("test");
        });
    }

    @Test
    public void updateCustomer() throws Exception {
        context.checking(new Expectations() {
            {
                oneOf(customerRepository).findByCustomerId(CUSTOMER_ID);
                will(returnValue(Optional.of(CUSTOMER)));
                oneOf(customerRepository).save(with(any(Customer.class)));
            }
        });

        CustomerService customerService = new CustomerServiceImpl(customerRepository);
        //Dapr pub/sub testing is handled by Pact testing
        Assertions.assertThrows(DaprException.class, () -> {
            customerService.updateCustomer(CUSTOMER_ID, API_CUSTOMER);
        });
    }

    @Test
    public void updateCustomerReturnsEmptyResults() {
        context.checking(new Expectations() {
            {
                oneOf(customerRepository).findByCustomerId("test");
                will(returnValue(Optional.empty()));
            }
        });

        CustomerService customerService = new CustomerServiceImpl(customerRepository);
        Assertions.assertThrows(NotFoundException.class, () -> {
            customerService.updateCustomer("test", API_CUSTOMER);
        });
    }

    @Test
    public void deleteCustomer() throws Exception {
        context.checking(new Expectations() {
            {
                oneOf(customerRepository).findByCustomerId(CUSTOMER_ID);
                will(returnValue(Optional.of(CUSTOMER)));
                oneOf(customerRepository).delete(with(any(Customer.class)));
            }
        });

        CustomerService customerService = new CustomerServiceImpl(customerRepository);

        //Dapr pub/sub testing is handled by Pact testing
        Assertions.assertThrows(DaprException.class, () -> {
            customerService.deleteCustomer(CUSTOMER_ID);
        });
    }

    @Test
    public void deleteCustomerReturnsEmptyResults() {
        context.checking(new Expectations() {
            {
                oneOf(customerRepository).findByCustomerId("test");
                will(returnValue(Optional.empty()));
            }
        });

        CustomerService customerService = new CustomerServiceImpl(customerRepository);
        Assertions.assertThrows(NotFoundException.class, () -> {
            customerService.deleteCustomer("test");
        });
    }
}
