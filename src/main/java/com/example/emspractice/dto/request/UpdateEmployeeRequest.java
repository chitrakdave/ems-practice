package com.example.emspractice.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateEmployeeRequest{

    private String name;
    private int age;

}


