package com.github.wenqiglantz.service.customerservice.restcontroller.advice;

import com.github.wenqiglantz.service.customerservice.data.exception.NotFoundException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;

public class GeneralExceptionControllerAdviceTest {
    private static final String UNIQUE_IDENTIFIER = "UNIQUE_IDENTIFIER";
    private static final String DESCRIPTION = "DESCRIPTION";

    @Test
    public void handleHttpRequestMethodNotSupportedException() {
        String description = "Unexpected system exception ID: " + UNIQUE_IDENTIFIER;
        HttpRequestMethodNotSupportedException exception = new HttpRequestMethodNotSupportedException(description);
        GeneralExceptionControllerAdvice advice = new GeneralExceptionControllerAdvice();
        ResponseEntity responseEntity = advice.handleHttpRequestMethodNotSupportedException(exception);
        MatcherAssert.assertThat(responseEntity.getStatusCode(), Matchers.is(Matchers.equalTo(HttpStatus.BAD_REQUEST)));
    }

    @Test
    public void handleRuntimeException() {
        String description = "Unexpected system exception ID: " + UNIQUE_IDENTIFIER;
        RuntimeException exception = new RuntimeException(description);
        GeneralExceptionControllerAdvice advice = new GeneralExceptionControllerAdvice();
        ResponseEntity responseEntity = advice.handleRuntimeException(exception);
        MatcherAssert.assertThat(responseEntity.getStatusCode(), Matchers.is(Matchers.equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @Test
    public void handleNotFoundException() {
        NotFoundException exception = new NotFoundException(DESCRIPTION);
        GeneralExceptionControllerAdvice advice = new GeneralExceptionControllerAdvice();
        ResponseEntity responseEntity = advice.handleNotFoundException(exception);
        MatcherAssert.assertThat(responseEntity.getStatusCode(), Matchers.is(Matchers.equalTo(HttpStatus.NOT_FOUND)));
    }
}
