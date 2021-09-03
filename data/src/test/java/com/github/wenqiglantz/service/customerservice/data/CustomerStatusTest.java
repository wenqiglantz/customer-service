package com.github.wenqiglantz.service.customerservice.data;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

public class CustomerStatusTest {

    private static final String CREATED = "CREATED";
    private static final String UPDATED = "UPDATED";
    private static final String DELETED = "DELETED";

    @Test
    public void getName() {
        MatcherAssert.assertThat(CustomerStatus.CREATED.value(), Matchers.is(Matchers.equalTo(CREATED)));
        MatcherAssert.assertThat(CustomerStatus.UPDATED.value(), Matchers.is(Matchers.equalTo(UPDATED)));
        MatcherAssert.assertThat(CustomerStatus.DELETED.value(), Matchers.is(Matchers.equalTo(DELETED)));
    }

    @Test
    public void fromValue(){
        MatcherAssert.assertThat(CustomerStatus.fromValue(CREATED), Matchers.is(Matchers.equalTo(CustomerStatus.CREATED)));
        MatcherAssert.assertThat(CustomerStatus.fromValue(UPDATED), Matchers.is(Matchers.equalTo(CustomerStatus.UPDATED)));
        MatcherAssert.assertThat(CustomerStatus.fromValue(DELETED), Matchers.is(Matchers.equalTo(CustomerStatus.DELETED)));
    }
}
