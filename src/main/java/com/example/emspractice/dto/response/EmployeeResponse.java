package com.example.emspractice.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class EmployeeResponse{

    private long id;
    private String name;
    private int age;

    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private DepartmentResponse departmentResponse;

}
