package ru.vzhitelev.restfulllearning.map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.vzhitelev.restfulllearning.dto.DepartmentDto;
import ru.vzhitelev.restfulllearning.dto.EmployeeDto;
import ru.vzhitelev.restfulllearning.entity.Department;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    Department toEntity(DepartmentDto departmentDto);
    DepartmentDto toDto(Department department);
}
