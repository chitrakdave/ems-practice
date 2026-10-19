package com.example.emspractice.repository;

import com.example.emspractice.entity.DepartmentEntity;
import com.example.emspractice.repository.input.SearchDepartment;
import org.springframework.data.domain.Page;

public interface CustomDepartmentRepository {

    Page<DepartmentEntity> searchDepartments(SearchDepartment searchDepartment, int page, int size);
}
