package com.example.emspractice.controller;

import com.example.emspractice.dto.request.CreateDepartmentRequest;
import com.example.emspractice.dto.request.SearchDepartmentRequest;
import com.example.emspractice.dto.request.UpdateDepartmentRequest;
import com.example.emspractice.dto.response.DepartmentResponse;
import com.example.emspractice.dto.response.PaginateResponse;
import com.example.emspractice.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping("/department")
    public ResponseEntity<DepartmentResponse> createDepartment(@RequestBody @Valid CreateDepartmentRequest createDepartmentRequest){
        DepartmentResponse departmentResponse= departmentService.createDepartment(createDepartmentRequest);
        return ResponseEntity.ok(departmentResponse);
    }

    @GetMapping("/department")
    public ResponseEntity<PaginateResponse<DepartmentResponse>> getDepartments(
            @RequestParam(value="page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "name", required = false) String name){

        SearchDepartmentRequest searchDepartmentRequest= new SearchDepartmentRequest();

        searchDepartmentRequest.setName(name);
        searchDepartmentRequest.setPage(Math.max(--page,0));
        searchDepartmentRequest.setSize(size);

        PaginateResponse<DepartmentResponse> departments= departmentService.getDepartments(searchDepartmentRequest);

        return ResponseEntity.ok(departments);
    }

    @GetMapping("/department/{id}")
    public ResponseEntity<DepartmentResponse> getDepartmentById(@PathVariable long id){
        log.info("get dept by id: {}", id);
        DepartmentResponse departmentResponse=departmentService.getDepartmentById(id);
        return ResponseEntity.ok(departmentResponse);
    }

    @PutMapping("/department/{id}")
    public ResponseEntity<DepartmentResponse> updateDepartment(@PathVariable long id, @RequestBody UpdateDepartmentRequest updateDepartmentRequest){

        DepartmentResponse departmentResponse= departmentService.updateDepartment(id, updateDepartmentRequest);
        return ResponseEntity.ok(departmentResponse);
    }

    @DeleteMapping("/department/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable long id){
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok("dept with id "+ id+ " has been deleted successfully");
    }

}
