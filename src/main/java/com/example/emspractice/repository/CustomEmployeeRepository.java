package com.example.emspractice.repository;

import com.example.emspractice.entity.EmployeeEntity;
import com.example.emspractice.repository.input.SearchEmployee;
import org.springframework.data.domain.Page;

public interface CustomEmployeeRepository {

    Page<EmployeeEntity> searchEmployees(SearchEmployee searchEmployee, int page, int size);
}
