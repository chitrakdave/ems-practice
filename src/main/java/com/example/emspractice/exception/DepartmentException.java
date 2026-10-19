package com.example.emspractice.exception;

import lombok.Getter;

@Getter
public class DepartmentException extends RuntimeException{

    private final Error error;

    public DepartmentException(String message, Error error){
        super(message);
        this.error=error;
    }
}