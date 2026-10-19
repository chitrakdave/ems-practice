package com.example.emspractice.exception;

import lombok.Getter;

@Getter
public class EmployeeException extends RuntimeException {

    private final Error error;

    public EmployeeException(String message, Error error){
        super(message);
        this.error=error;
    }
}