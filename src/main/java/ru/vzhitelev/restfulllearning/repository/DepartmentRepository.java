package ru.vzhitelev.restfulllearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vzhitelev.restfulllearning.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
