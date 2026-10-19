package com.example.emspractice.repository;

import com.example.emspractice.entity.EmployeeEntity;
import com.example.emspractice.repository.input.SearchEmployee;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomEmployeeRepositoryImpl implements CustomEmployeeRepository {

    private final EntityManager entityManager;

    @Override
    public Page<EmployeeEntity> searchEmployees(SearchEmployee input, int page, int size) {

        String query = "select * from employee";

        if (StringUtils.isNotBlank(input.getName())) {
            query = query + addLikeQuery(query, "name", input.getName());
        }

        if (input.getAge() != null) {
            query = query + addAndQuery(query, "age", input.getAge());
        }

        Query nativeQuery = entityManager.createNativeQuery(query, EmployeeEntity.class)
                .setMaxResults(size)
                .setFirstResult(page * size);

        List<EmployeeEntity> employees = nativeQuery.getResultList();

        Query countQuery = entityManager.createNativeQuery("select count(*) from employee");
        long count = (long) countQuery.getSingleResult();

        return new PageImpl<>(employees, PageRequest.of(page, size), count);

    }

    private String addLikeQuery(String query, String key, String value){
        return " where "+ key+ " like '%"+value+ "%' ";
    }

    private String addAndQuery(String query, String key, int value){
        String andQuery="";

        if(!query.contains("where")){
            andQuery=" where ";
        }

        else{
            andQuery=" and ";
        }

        andQuery= andQuery+key+" = "+ value;
        return andQuery;
    }

}
