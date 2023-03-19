package com.example.restapi.validator;

public class ExceptionHandler extends RuntimeException {
    public ExceptionHandler(String message) {
        super(message);
    }
}
