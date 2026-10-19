package com.example.emspractice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CreateEmployeeRequest{

    @NotBlank(message="name is required")
    private String name;

    @Min(value=1, message="age cannot be less than 1")
    private int age;

    @NotNull(message = "salary cannot be null")
    @Min(value=1, message="salary cannot be negative or 0")
    private BigDecimal salary;

    private long deptId;

    private String deptName;
}