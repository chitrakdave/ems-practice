package com.example.emspractice.service;

import com.example.emspractice.dto.request.CreateEmployeeRequest;
import com.example.emspractice.dto.request.SearchEmployeeRequest;
import com.example.emspractice.dto.request.UpdateEmployeeRequest;
import com.example.emspractice.dto.response.EmployeeResponse;
import com.example.emspractice.dto.response.PaginateResponse;
import com.example.emspractice.entity.DepartmentEntity;
import com.example.emspractice.entity.EmployeeEntity;
import com.example.emspractice.exception.DepartmentException;
import com.example.emspractice.exception.EmployeeException;
import com.example.emspractice.repository.EmployeeRepository;
import com.example.emspractice.repository.input.SearchEmployee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import com.example.emspractice.exception.Error;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;

    private EmployeeResponse mapToResponse(EmployeeEntity employeeEntity) {

        EmployeeResponse employeeResponse = new EmployeeResponse();

        employeeResponse.setId(employeeEntity.getId());
        employeeResponse.setName(employeeEntity.getName());
        employeeResponse.setAge(employeeEntity.getAge());

        employeeResponse.setActive(employeeEntity.isActive());
        employeeResponse.setCreatedAt(employeeEntity.getCreatedAt());
        employeeResponse.setUpdatedAt(employeeEntity.getUpdatedAt());

        employeeResponse.setDepartmentResponse(departmentService.mapToResponse(employeeEntity.getDepartmentEntity()));

        return employeeResponse;
    }

    public EmployeeResponse createEmployee(CreateEmployeeRequest createEmployeeRequest) {
        checkIfUserAlreadyExists(createEmployeeRequest.getName());

        DepartmentEntity departmentEntity = getDepartment(createEmployeeRequest);
        EmployeeEntity employeeEntity = new EmployeeEntity();

        employeeEntity.setName(createEmployeeRequest.getName());
        employeeEntity.setAge(createEmployeeRequest.getAge());
        employeeEntity.setDepartmentEntity(departmentEntity);
        employeeEntity.setActive(true);

        employeeRepository.save(employeeEntity);
        return mapToResponse(employeeEntity);
    }

    private DepartmentEntity getDepartment(CreateEmployeeRequest createEmployeeRequest) {
        try {
            return departmentService.getDepartment(createEmployeeRequest.getDeptId());
        } catch (DepartmentException e) {
            log.error("Error while fetching department", e);
            return departmentService.createDepartment(createEmployeeRequest.getDeptName());
        }
    }

    private void checkIfUserAlreadyExists(String name) {
        List<EmployeeEntity> employees = employeeRepository.findByName(name);

        if (employees.size() > 0) {
            throw new EmployeeException("emp already exist with this name", Error.EMPLOYEE_ALREADY_EXISTS);
        }

    }

    public PaginateResponse<EmployeeResponse> getEmployees(SearchEmployeeRequest searchEmployeeRequest) {

        SearchEmployee searchEmployee = new SearchEmployee();
        searchEmployee.setName(searchEmployeeRequest.getName());
        searchEmployee.setAge(searchEmployeeRequest.getAge());

        Page<EmployeeEntity> pageData = employeeRepository.searchEmployees(searchEmployee, searchEmployeeRequest.getPage(), searchEmployeeRequest.getSize());

        PaginateResponse<EmployeeResponse> paginateResponse = new PaginateResponse<>();

        paginateResponse.setData(pageData.getContent().stream().map(this::mapToResponse).toList());
        paginateResponse.setTotalCount(pageData.getTotalElements());
        paginateResponse.setTotalPages(pageData.getTotalPages());

        paginateResponse.setSize(pageData.getSize());
        paginateResponse.setPage(pageData.getNumber() + 1);

        return paginateResponse;
    }

    public EmployeeResponse getEmployeeById(long id) {
        EmployeeEntity employeeEntity = getEmployee(id);
        return mapToResponse(employeeEntity);
    }

    public EmployeeResponse updateEmployee(long id, UpdateEmployeeRequest updateEmployeeRequest) {

        EmployeeEntity employeeEntity = getEmployee(id);
        employeeEntity.setName(updateEmployeeRequest.getName());
        employeeEntity.setAge(updateEmployeeRequest.getAge());

        employeeRepository.save(employeeEntity);

        return mapToResponse(employeeEntity);
    }


    private EmployeeEntity getEmployee(long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeException("Emp not found", Error.EMPLOYEE_NOT_FOUND));
    }

    public void deleteEmployee(long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeException("emp not found", Error.EMPLOYEE_NOT_FOUND);
        }

        employeeRepository.deleteById(id);
        System.out.println("Employee with " + id + " has been deleted successfully");
    }

}
