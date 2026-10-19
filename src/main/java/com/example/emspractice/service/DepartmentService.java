package com.example.emspractice.service;

import com.example.emspractice.dto.request.CreateDepartmentRequest;
import com.example.emspractice.dto.request.SearchDepartmentRequest;
import com.example.emspractice.dto.request.UpdateDepartmentRequest;
import com.example.emspractice.dto.response.DepartmentResponse;
import com.example.emspractice.dto.response.PaginateResponse;
import com.example.emspractice.entity.DepartmentEntity;
import com.example.emspractice.exception.DepartmentException;
import com.example.emspractice.exception.Error;
import com.example.emspractice.repository.DepartmentRepository;
import com.example.emspractice.repository.input.SearchDepartment;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentResponse mapToResponse(DepartmentEntity departmentEntity) {

        DepartmentResponse departmentResponse = new DepartmentResponse();

        departmentResponse.setId(departmentEntity.getId());
        departmentResponse.setName(departmentEntity.getName());
        departmentResponse.setActive(departmentEntity.isActive());

        departmentResponse.setCreatedAt(departmentEntity.getCreatedAt());
        departmentResponse.setUpdatedAt(departmentEntity.getUpdatedAt());

        return departmentResponse;

    }

    public DepartmentResponse createDepartment(CreateDepartmentRequest createDepartmentRequest) {

        DepartmentEntity departmentEntity = createDepartment(createDepartmentRequest.getName());
        return mapToResponse(departmentEntity);

    }

    public DepartmentEntity createDepartment(String deptName) {
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setName(deptName);
        departmentEntity.setActive(true);

        return departmentRepository.save(departmentEntity);
    }

    public PaginateResponse<DepartmentResponse> getDepartments(SearchDepartmentRequest searchDepartmentRequest) {

        SearchDepartment searchDepartment = new SearchDepartment();
        searchDepartment.setName(searchDepartmentRequest.getName());

        Page<DepartmentEntity> pageData = departmentRepository.searchDepartments(searchDepartment, searchDepartmentRequest.getPage(), searchDepartmentRequest.getSize());

        PaginateResponse<DepartmentResponse> paginateResponse = new PaginateResponse<>();

        paginateResponse.setData(pageData.getContent()
                .stream().map(this::mapToResponse).toList());

        paginateResponse.setTotalCount(pageData.getTotalElements());
        paginateResponse.setTotalPages(pageData.getTotalPages());
        paginateResponse.setSize(pageData.getSize());
        paginateResponse.setPage(pageData.getNumber() + 1);

        return paginateResponse;

    }

    @Transactional
    public DepartmentEntity getDepartment(long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentException("Dept not found", Error.DEPT_NOT_FOUND));
    }


    public DepartmentResponse getDepartmentById(long id) {
        DepartmentEntity departmentEntity = getDepartment(id);
        return mapToResponse(departmentEntity);
    }

    public DepartmentResponse updateDepartment(long id, UpdateDepartmentRequest updateDepartmentRequest) {

        DepartmentEntity departmentEntity = getDepartment(id);

        departmentEntity.setName(updateDepartmentRequest.getName());

        DepartmentEntity savedDepartment = departmentRepository.saveAndFlush(departmentEntity);

        return mapToResponse(savedDepartment);
    }

    public void deleteDepartment(long id) {
        if (!departmentRepository.existsById(id)) {
            throw new DepartmentException("dept not found", Error.DEPT_NOT_FOUND);
        }

        departmentRepository.deleteById(id);
        System.out.println("dept with id " + id + " has been deleted successfully");

    }

}
