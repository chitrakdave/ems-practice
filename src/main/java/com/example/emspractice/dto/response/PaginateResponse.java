package com.example.emspractice.dto.response;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PaginateResponse<T> {

    private List<T> data;
    private long totalCount;
    private int totalPages;

    private int size;
    private int page;
}