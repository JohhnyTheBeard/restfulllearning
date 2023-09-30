package ru.vzhitelev.restfulllearning.map;

import org.jetbrains.annotations.NotNull;
import ru.vzhitelev.restfulllearning.dto.EmployeeDTO;
import ru.vzhitelev.restfulllearning.entity.Employee;

public class EmployeeMapper {
    public static @NotNull EmployeeDTO mapToEmployeeDto(@NotNull Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setFirstName(employee.getFirstName());
        employeeDTO.setSurName(employee.getSurName());
        employeeDTO.setMSISDN(employee.getMSISDN());
        employeeDTO.setDepartmentId(employee.getDepartmentId());
        return employeeDTO;
    }
}
