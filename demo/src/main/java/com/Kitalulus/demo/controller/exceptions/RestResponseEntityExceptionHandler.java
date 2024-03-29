package com.Kitalulus.demo.controller.exceptions;

import java.sql.SQLException;
import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.Kitalulus.demo.dto.responce.BaseResponseDto;

import lombok.extern.slf4j.Slf4j;

//TODO: Decide status code for hardcoded values.
@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<BaseResponseDto<ErrorDTO>> handleDomainException(DomainException ex) {
        var errorDto = ErrorDTO.builder().timestamp(new Date()).message(ex.getMessage()).data(ex.getData()).build();
        return ResponseEntity.status(ex.getHttpStatus()).body(BaseResponseDto.failure(ex.getStatusCode(), errorDto));
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<BaseResponseDto<ErrorDTO>> sqlException(SQLException ex) {
        var errorDto = ErrorDTO.builder().timestamp(new Date()).message("SQl Exception for invalid key").data(null)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponseDto.failure("002", errorDto));
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        final StringBuilder message = new StringBuilder();
        ex.getFieldErrors().forEach((val) -> {
            message.append(val.getDefaultMessage()).append(", ");
        });
        message.delete(message.lastIndexOf(", "), message.length());
        var errorDto = ErrorDTO.builder().timestamp(new Date()).message(message.toString()).data(null).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponseDto.failure("001", errorDto));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleBindException(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        var errorDto = ErrorDTO.builder().timestamp(new Date()).message(ex.getMessage()).data(null).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponseDto.failure("003", errorDto));
    }

}