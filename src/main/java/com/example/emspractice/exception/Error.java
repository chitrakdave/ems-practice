package com.example.emspractice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum Error {

    EMPLOYEE_NOT_FOUND(HttpStatus.NOT_FOUND, "EMP_01"),

    EMPLOYEE_ALREADY_EXISTS(HttpStatus.CONFLICT,"EMP_02"),

    DEPT_NOT_FOUND(HttpStatus.NOT_FOUND,"DEPT_01");

    private final HttpStatus httpStatus;

    private final String code;
}