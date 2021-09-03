package com.github.wenqiglantz.service.customerservice.data.exception;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

public class NotFoundExceptionTest {
    private static final String MESSAGE = "MESSAGE";

    @Test
    public void notFoundException() {
        NotFoundException exception = new NotFoundException(MESSAGE);
        MatcherAssert.assertThat(exception.getMessage(), Matchers.is(Matchers.equalTo(MESSAGE)));
    }
}
