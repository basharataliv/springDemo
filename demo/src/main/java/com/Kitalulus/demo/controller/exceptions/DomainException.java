package com.Kitalulus.demo.controller.exceptions;

import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class DomainException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private final HttpStatus httpStatus;
    private final String statusCode;
    private final Map<String, Object> data;

    public DomainException(HttpStatus httpStatus, String statusCode, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.statusCode = statusCode;
        this.data = null;
    }

    public DomainException(HttpStatus httpStatus, String statusCode, String message, Map<String, Object> data) {
        super(message);
        this.statusCode = statusCode;
        this.httpStatus = httpStatus;
        this.data = data;
    }
}
