package ru.vzhitelev.restfulllearning.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "department_dict")
@Getter
@Setter
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

//    @OneToMany(mappedBy = "departmentId")
//    List<Employee> employees;
}
