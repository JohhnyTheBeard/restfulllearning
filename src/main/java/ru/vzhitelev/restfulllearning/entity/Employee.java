package ru.vzhitelev.restfulllearning.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "surname")
    private String surName;

    @Column(name = "msisdn", unique = true)
    private String MSISDN;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department departmentId;

    public Employee(String firstName, String surName, Department departmentId) {
        this.firstName = firstName;
        this.surName = surName;
        this.departmentId = departmentId;
    }

    public Employee(String firstName, String surName, String MSISDN, Department departmentId) {
        this.firstName = firstName;
        this.surName = surName;
        this.MSISDN = MSISDN;
        this.departmentId = departmentId;
    }

    public String getFullName() {
        return firstName + " " + surName;
    }

}