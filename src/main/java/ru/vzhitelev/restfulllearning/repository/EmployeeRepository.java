package ru.vzhitelev.restfulllearning.repository;


import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.vzhitelev.restfulllearning.entity.Department;
import ru.vzhitelev.restfulllearning.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByOrderById();
    List<Employee> findEmployeeByDepartmentId(Department departmentId);
    Optional<Employee> findEmployeeByMSISDN(String msisdn);
    List<Employee> findEmployeeByMSISDNAndDepartmentId(String msisdn, Department departmentId);

}
