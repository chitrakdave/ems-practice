package com.example.emspractice.advice;

import com.example.emspractice.dto.response.ErrorResponse;
import com.example.emspractice.exception.DepartmentException;
import com.example.emspractice.exception.EmployeeException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(exception = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception,
                                                                               HttpServletRequest httpServletRequest) {

        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setErrorCode("invalid input");
        errorResponse.setMessage(exception.getBindingResult().getFieldError().getDefaultMessage());
        errorResponse.setPath(httpServletRequest.getRequestURI());
        errorResponse.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

    }

    @ExceptionHandler(exception = EmployeeException.class)
    public ResponseEntity<ErrorResponse> handleEmployeeException(EmployeeException employeeException,
                                                                 HttpServletRequest httpServletRequest) {

        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setErrorCode(employeeException.getError().getCode());
        errorResponse.setMessage(employeeException.getMessage());
        errorResponse.setPath(httpServletRequest.getRequestURI());
        errorResponse.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(employeeException.getError().getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(exception = DepartmentException.class)
    public ResponseEntity<ErrorResponse> handleDepartmentResponse(DepartmentException departmentException,
                                                                  HttpServletRequest httpServletRequest) {

        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setErrorCode(departmentException.getError().getCode());
        errorResponse.setMessage(departmentException.getMessage());
        errorResponse.setPath(httpServletRequest.getRequestURI());
        errorResponse.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(departmentException.getError().getHttpStatus()).body(errorResponse);

    }


}