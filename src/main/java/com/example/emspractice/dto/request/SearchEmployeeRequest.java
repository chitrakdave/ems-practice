package com.example.emspractice.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchEmployeeRequest{

    private String name;
    private Integer age;
    private int page;
    private int size;
}
