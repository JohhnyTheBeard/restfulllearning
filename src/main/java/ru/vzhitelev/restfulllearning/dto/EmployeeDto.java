package ru.vzhitelev.restfulllearning.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.vzhitelev.restfulllearning.entity.Department;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private Long id;
    private String firstName;
    private String surName;
    private String MSISDN;
    private DepartmentDto departmentId;

    public String getFullTheName(){
        return firstName + " the " + surName;
    }
}
