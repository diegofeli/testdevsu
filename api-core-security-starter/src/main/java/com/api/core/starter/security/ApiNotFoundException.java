package com.api.core.starter.security;

import org.springframework.http.HttpStatus;

public class ApiNotFoundException extends ApiException {
    public ApiNotFoundException(int code, String message) {
        super(code, message, HttpStatus.NOT_FOUND);
    }
}
