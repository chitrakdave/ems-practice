package com.example.emspractice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateDepartmentRequest{

    @NotBlank(message = "department name is required")
    private String name;
}
