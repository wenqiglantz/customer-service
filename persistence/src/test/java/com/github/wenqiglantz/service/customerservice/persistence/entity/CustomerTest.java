package com.github.wenqiglantz.service.customerservice.persistence.entity;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

public class CustomerTest {

    private static final String CUSTOMER_ID = "ABCDEFG12345678910HIJKLMNOP12345";
    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String LAST_NAME = "LAST_NAME";

    @Test
    public void objectCreation() {
        Customer object = Customer.builder()
                .customerId(CUSTOMER_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();
        MatcherAssert.assertThat(object.getCustomerId(), Matchers.is(Matchers.sameInstance(CUSTOMER_ID)));
        MatcherAssert.assertThat(object.getFirstName(), Matchers.is(Matchers.sameInstance(FIRST_NAME)));
        MatcherAssert.assertThat(object.getLastName(), Matchers.is(Matchers.sameInstance(LAST_NAME)));
    }
}
