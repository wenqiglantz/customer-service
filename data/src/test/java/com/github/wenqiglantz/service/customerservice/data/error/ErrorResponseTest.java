package com.github.wenqiglantz.service.customerservice.data.error;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

public class ErrorResponseTest {

    private static final String ERROR_CODE = "ERROR_CODE";
    private static final String ERROR_KEY = "ERROR_KEY";
    private static final String ERROR_MESSAGE = "ERROR_MESSAGE";

    @Test
    public void objectCreation() {
        ErrorResponse object = ErrorResponse.builder()
                .errorCode(ERROR_CODE)
                .errorKey(ERROR_KEY)
                .errorMessage(ERROR_MESSAGE)
                .build();
        MatcherAssert.assertThat(object.getErrorCode(), Matchers.is(Matchers.sameInstance(ERROR_CODE)));
        MatcherAssert.assertThat(object.getErrorKey(), Matchers.is(Matchers.sameInstance(ERROR_KEY)));
        MatcherAssert.assertThat(object.getErrorMessage(), Matchers.is(Matchers.sameInstance(ERROR_MESSAGE)));
    }
}
