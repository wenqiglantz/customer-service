package com.github.wenqiglantz.service.customerservice.restcontroller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;

import com.github.wenqiglantz.service.customerservice.data.CustomerInfo;
import com.github.wenqiglantz.service.customerservice.service.CustomerService;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.junit5.JUnit5Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

public class CustomerControllerTest {
    private static final String CUSTOMER_ID = "ABCDEFG12345678910HIJKLMNOP12345";
    private static final String FIRST_NAME = "FIRST";
    private static final String LAST_NAME = "LAST";

    private static final CustomerInfo API_CUSTOMER = CustomerInfo.builder()
            .firstName(FIRST_NAME)
            .lastName(LAST_NAME)
            .build();

    private static final String PATH = "/customers/{customerId}";
    private static final UriComponentsBuilder URI_COMPONENTS_BUILDER = UriComponentsBuilder.fromPath(PATH);

    private UriComponentsBuilder uriBuilder;

    private CustomerService customerService;

    @RegisterExtension
    public Mockery context = new JUnit5Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    @BeforeEach
    public void setUp() {
        uriBuilder = context.mock(UriComponentsBuilder.class);
        customerService = context.mock(CustomerService.class);
    }

    @Test
    public void createCustomer() throws Exception {
        context.checking(new Expectations() {
            {
                oneOf(customerService).saveCustomer(API_CUSTOMER);
                will(returnValue(API_CUSTOMER));
                oneOf(uriBuilder).path(PATH);
                will(returnValue(URI_COMPONENTS_BUILDER));
            }
        });

        CustomerController customerController = new CustomerController(customerService);

        ResponseEntity responseEntity = customerController.createCustomer(API_CUSTOMER, uriBuilder);
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.CREATED)));
    }

    @Test
    public void getCustomers() {
        Pageable pageable = PageRequest.of(0, 1);
        context.checking(new Expectations(){
            {
                oneOf(customerService).getCustomers(pageable);
                will(returnValue(new PageImpl<>(Collections.singletonList(API_CUSTOMER))));
            }
        });

        CustomerController customerController = new CustomerController(customerService);

        ResponseEntity responseEntity = customerController.getCustomers(Pageable.ofSize(1));
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.OK)));
        assertThat(responseEntity.getBody(), is(notNullValue()));
    }

    @Test
    public void getCustomer() {
        context.checking(new Expectations(){
            {
                oneOf(customerService).getCustomer(CUSTOMER_ID);
                will(returnValue(API_CUSTOMER));
            }
        });

        CustomerController customerController = new CustomerController(customerService);

        ResponseEntity responseEntity = customerController.getCustomer(CUSTOMER_ID);
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.OK)));
        assertThat(responseEntity.getBody(), is(sameInstance(API_CUSTOMER)));
    }

    @Test
    public void updateCustomer() throws Exception {
        CustomerInfo customerInfoNew = CustomerInfo.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();

        context.checking(new Expectations(){
            {
                oneOf(customerService).updateCustomer(CUSTOMER_ID, customerInfoNew);
            }
        });

        CustomerController customerController = new CustomerController(customerService);

        ResponseEntity responseEntity = customerController.updateCustomer(CUSTOMER_ID, customerInfoNew);
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.NO_CONTENT)));
        assertThat(responseEntity.getBody(), is(nullValue()));
    }

    @Test
    public void deleteCustomer() throws Exception {
        context.checking(new Expectations(){
            {
                oneOf(customerService).deleteCustomer(CUSTOMER_ID);
            }
        });

        CustomerController customerController = new CustomerController(customerService);

        ResponseEntity responseEntity = customerController.deleteCustomer(CUSTOMER_ID);
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.NO_CONTENT)));
        assertThat(responseEntity.getBody(), is(nullValue()));
        assertThat(responseEntity.getBody(), is(nullValue()));
    }
}
