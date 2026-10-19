package com.example.emspractice.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchDepartmentRequest{

    private String name;
    private int page;
    private int size;
}

