package com.example.emspractice.controller;


import com.example.emspractice.dto.request.CreateEmployeeRequest;
import com.example.emspractice.dto.request.SearchEmployeeRequest;
import com.example.emspractice.dto.request.UpdateEmployeeRequest;
import com.example.emspractice.dto.response.EmployeeResponse;
import com.example.emspractice.dto.response.PaginateResponse;
import com.example.emspractice.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/employee")
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody @Valid CreateEmployeeRequest createEmployeeRequest){

        log.info("create emp request: {}", createEmployeeRequest);
        EmployeeResponse employeeResponse= employeeService.createEmployee(createEmployeeRequest);
        return ResponseEntity.ok(employeeResponse);

    }

    @GetMapping("/employee")
    public ResponseEntity<PaginateResponse<EmployeeResponse>> getEmployees(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "age", required = false) Integer age
    ){

        SearchEmployeeRequest searchEmployeeRequest= new SearchEmployeeRequest();

        searchEmployeeRequest.setName(name);
        searchEmployeeRequest.setAge(age);
        searchEmployeeRequest.setPage(Math.max(--page,0));
        searchEmployeeRequest.setSize(size);

        PaginateResponse<EmployeeResponse> employees=employeeService.getEmployees(searchEmployeeRequest);

        return ResponseEntity.ok(employees);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable long id){
        log.info("get emp by id: {}", id);
        EmployeeResponse employeeResponse=employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employeeResponse);

    }

    @PutMapping("employee/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable long id, @RequestBody UpdateEmployeeRequest updateEmployeeRequest){

        EmployeeResponse employeeResponse= employeeService.updateEmployee(id, updateEmployeeRequest);
        return ResponseEntity.ok(employeeResponse);
    }

    @DeleteMapping("employee/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable long id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("emp with id "+id+" has been deleted successfully");
    }

}
