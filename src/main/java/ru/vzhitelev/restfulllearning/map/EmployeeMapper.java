package ru.vzhitelev.restfulllearning.map;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.vzhitelev.restfulllearning.dto.EmployeeDto;
import ru.vzhitelev.restfulllearning.entity.Employee;

@Mapper(componentModel = "spring") //, uses = DepartmentMapper.class)
public interface EmployeeMapper {
    EmployeeMapper MAPPER = Mappers.getMapper(EmployeeMapper.class);
    Employee toEntity(EmployeeDto source);
    EmployeeDto toDto(Employee target);

}
