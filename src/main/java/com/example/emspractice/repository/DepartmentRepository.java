package com.example.emspractice.repository;

import com.example.emspractice.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long>, CustomDepartmentRepository {

    List<DepartmentEntity> findByName(String name);
}
