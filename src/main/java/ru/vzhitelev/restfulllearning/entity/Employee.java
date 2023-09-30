package ru.vzhitelev.restfulllearning.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "employee")
@Getter
@Setter
@ToString
public class Employee {
    @Id
//    @SequenceGenerator(name = "employee_sequence", sequenceName = "employee_sequence")
    @GeneratedValue(strategy=GenerationType.IDENTITY) //, generator = "employee_sequence")
    private Long id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "surname")
    private String surName;

    @Column(name = "msisdn", unique = true)
    private String MSISDN;

//    @Column(name = )
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department departmentId;


    public Employee() {}

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

