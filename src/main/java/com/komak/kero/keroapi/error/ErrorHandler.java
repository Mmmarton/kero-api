package com.komak.kero.keroapi.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandler {

    private static final String INVALID_DATA = "INVALID_DATA";

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessage handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return new ErrorMessage(HttpStatus.BAD_REQUEST, INVALID_DATA);
    }
}
