package com.github.wenqiglantz.service.customerservice.data.error;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

public class ErrorTypeTest {
    private static final String INVALID_REQUEST_DATA = "INVALID_REQUEST_DATA";
    private static final String UNKNOWN_DATA_ITEM = "UNKNOWN_DATA_ITEM";
    private static final String DATA_ALREADY_EXISTS = "DATA_ALREADY_EXISTS";
    private static final String UNEXPECTED_ERROR = "UNEXPECTED_ERROR";
    private static final String UPSTREAM_SERVICE_ERROR = "UPSTREAM_SERVICE_ERROR";
    private static final String UNAUTHORIZED_REQUEST = "UNAUTHORIZED_REQUEST";
    private static final String FORBIDDEN_REQUEST = "FORBIDDEN_REQUEST";

    @Test
    public void getName() {
        MatcherAssert.assertThat(ErrorType.INVALID_REQUEST_DATA.name(), Matchers.is(Matchers.equalTo(INVALID_REQUEST_DATA)));
        MatcherAssert.assertThat(ErrorType.UNKNOWN_DATA_ITEM.name(), Matchers.is(Matchers.equalTo(UNKNOWN_DATA_ITEM)));
        MatcherAssert.assertThat(ErrorType.DATA_ALREADY_EXISTS.name(), Matchers.is(Matchers.equalTo(DATA_ALREADY_EXISTS)));
        MatcherAssert.assertThat(ErrorType.UNEXPECTED_ERROR.name(), Matchers.is(Matchers.equalTo(UNEXPECTED_ERROR)));
        MatcherAssert.assertThat(ErrorType.UPSTREAM_SERVICE_ERROR.name(), Matchers.is(Matchers.equalTo(UPSTREAM_SERVICE_ERROR)));
        MatcherAssert.assertThat(ErrorType.UNAUTHORIZED_REQUEST.name(), Matchers.is(Matchers.equalTo(UNAUTHORIZED_REQUEST)));
        MatcherAssert.assertThat(ErrorType.FORBIDDEN_REQUEST.name(), Matchers.is(Matchers.equalTo(FORBIDDEN_REQUEST)));
    }

    @Test
    public void fromValue(){
        MatcherAssert.assertThat(ErrorType.INVALID_REQUEST_DATA, Matchers.is(Matchers.equalTo(ErrorType.fromValue(INVALID_REQUEST_DATA))));
        MatcherAssert.assertThat(ErrorType.UNKNOWN_DATA_ITEM, Matchers.is(Matchers.equalTo(ErrorType.fromValue(UNKNOWN_DATA_ITEM))));
        MatcherAssert.assertThat(ErrorType.DATA_ALREADY_EXISTS, Matchers.is(Matchers.equalTo(ErrorType.fromValue(DATA_ALREADY_EXISTS))));
        MatcherAssert.assertThat(ErrorType.UNEXPECTED_ERROR, Matchers.is(Matchers.equalTo(ErrorType.fromValue(UNEXPECTED_ERROR))));
        MatcherAssert.assertThat(ErrorType.UPSTREAM_SERVICE_ERROR, Matchers.is(Matchers.equalTo(ErrorType.fromValue(UPSTREAM_SERVICE_ERROR))));
        MatcherAssert.assertThat(ErrorType.UNAUTHORIZED_REQUEST, Matchers.is(Matchers.equalTo(ErrorType.fromValue(UNAUTHORIZED_REQUEST))));
        MatcherAssert.assertThat(ErrorType.FORBIDDEN_REQUEST, Matchers.is(Matchers.equalTo(ErrorType.fromValue(FORBIDDEN_REQUEST))));
    }
}
