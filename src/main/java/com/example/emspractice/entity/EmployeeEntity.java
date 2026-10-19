package com.example.emspractice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="employee")
@Data
@NoArgsConstructor
@ToString(exclude = "department")
@EqualsAndHashCode(callSuper = true, exclude = "department")
public class EmployeeEntity extends BaseEntity {

    @Column(name="name")
    private String name;

    @Column(name="age")
    private int age;

    @Column(name="active")
    private boolean active;

    @JoinColumn(name="dept_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private DepartmentEntity departmentEntity;
}
