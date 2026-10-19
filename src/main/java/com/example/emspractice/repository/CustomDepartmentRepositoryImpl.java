package com.example.emspractice.repository;

import com.example.emspractice.entity.DepartmentEntity;
import com.example.emspractice.repository.input.SearchDepartment;
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
public class CustomDepartmentRepositoryImpl implements CustomDepartmentRepository {

    private final EntityManager entityManager;

    @Override
    public Page<DepartmentEntity> searchDepartments(SearchDepartment searchDepartment, int page, int size) {

        String query = "select * from department";

        if (StringUtils.isNotBlank(searchDepartment.getName())) {
            query = query + addLikeQuery(query, "name", searchDepartment.getName());
        }

        Query nativeQuery = entityManager.createNativeQuery(query, DepartmentEntity.class)
                .setMaxResults(size)
                .setFirstResult(page * size);

        List<DepartmentEntity> departments = nativeQuery.getResultList();

        Query countQuery = entityManager.createNativeQuery("select count(*) from department");
        long count = (long) countQuery.getSingleResult();

        return new PageImpl<>(departments, PageRequest.of(page, size), count);
    }

    private String addLikeQuery(String query, String key, String value) {

        return " where " + key + " like '%" + value + "%' ";
    }

}
