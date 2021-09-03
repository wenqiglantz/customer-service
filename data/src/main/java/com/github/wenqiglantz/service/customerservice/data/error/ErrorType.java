package com.github.wenqiglantz.service.customerservice.data.error;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public enum ErrorType {
    INVALID_REQUEST_DATA,
    UNKNOWN_DATA_ITEM,
    DATA_ALREADY_EXISTS,
    UNEXPECTED_ERROR,
    UPSTREAM_SERVICE_ERROR,
    UNAUTHORIZED_REQUEST,
    FORBIDDEN_REQUEST;

    public static ErrorType fromValue(String v) {
        return valueOf(v);
    }
}

