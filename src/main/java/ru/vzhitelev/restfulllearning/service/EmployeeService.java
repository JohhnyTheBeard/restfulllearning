package ru.vzhitelev.restfulllearning.service;

import org.jetbrains.annotations.NotNull;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.vzhitelev.restfulllearning.dto.EmployeeDto;
import ru.vzhitelev.restfulllearning.entity.Department;
import ru.vzhitelev.restfulllearning.entity.Employee;
import ru.vzhitelev.restfulllearning.map.EmployeeMapper;
import ru.vzhitelev.restfulllearning.repository.DepartmentRepository;
import ru.vzhitelev.restfulllearning.repository.EmployeeRepository;
import ru.vzhitelev.restfulllearning.response.RestApiException;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService implements EmployeeServiceInt {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<Employee> list() {
        return employeeRepository.findByOrderById();
    }

    public List<EmployeeDto> queryList(Long departmentId, String msisdn) {
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        if (departmentId == null && msisdn == null) {
            employeeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                    .forEach(
                            employee -> employeeDtoList.add(EmployeeMapper.MAPPER.toDto(employee))
                    );
            return employeeDtoList;
        }
        if (departmentId == null) {
            employeeRepository.findEmployeeByMSISDN(msisdn)
                    .stream()
                    .toList()
                    .forEach(
                            employee -> employeeDtoList.add(EmployeeMapper.MAPPER.toDto(employee))
                    );
            return employeeDtoList;
        }
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RestApiException("Employee with id: " + departmentId + " not found"));
        if (msisdn == null) {
            employeeRepository.findEmployeeByDepartmentId(department)
                    .forEach(
                            employee -> employeeDtoList.add(EmployeeMapper.MAPPER.toDto(employee))
                    );
            return employeeDtoList;
        } else {
            employeeRepository.findEmployeeByMSISDNAndDepartmentId(msisdn, department)
                    .forEach(
                            employee -> employeeDtoList.add(EmployeeMapper.MAPPER.toDto(employee))
                    );
            return employeeDtoList;
        }
    }

    public EmployeeDto getById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RestApiException("Employee with id: " + id + " not found"));
        return EmployeeMapper.MAPPER.toDto(employee);
    }

    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.MAPPER.toEntity(employeeDto);
        if (employeeRepository.findEmployeeByMSISDN(employee.getMSISDN()).isPresent())
            throw new RestApiException("MSISDN is busy");
        return EmployeeMapper.MAPPER.toDto(employeeRepository.save(employee));
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.MAPPER.toEntity(employeeDto);

        Employee updateEmployee = employeeRepository.findById(employee.getId())
                .orElseThrow(() -> new RestApiException("Employee with id: " + employee.getId() + " not found"));
        if (!employee.getFirstName().isEmpty()) {
            updateEmployee.setFirstName(employee.getFirstName());
        }
        if (!employee.getSurName().isEmpty()) {
            updateEmployee.setSurName(employee.getSurName());
        }
        if (!employee.getMSISDN().isEmpty())
            updateEmployee.setMSISDN(employee.getMSISDN());
        if (employee.getDepartmentId() != null)
            updateEmployee.setDepartmentId(employee.getDepartmentId());

        employeeRepository.save(updateEmployee);
        return EmployeeMapper.MAPPER.toDto(updateEmployee);
    }

    public EmployeeDto removeDepartment(@NotNull Long id) {
        Employee employee = employeeRepository.findById(id).
                orElseThrow(() -> new RestApiException("Employee with id: " + id + " not found"));
        employee.setDepartmentId(null);
        Employee updateEmployee = employeeRepository.save(employee);
        return EmployeeMapper.MAPPER.toDto(updateEmployee);
    }

    public EmployeeDto setDepartment(@NotNull Long id, @NotNull Department department) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RestApiException("Employee with id: " + id + " not found"));
        employee.setDepartmentId(departmentRepository.findById(department.getId())
                .orElseThrow(()-> new RestApiException(String.format("Department with id: %d not found", department.getId()))));
        Employee upadteEmployee = employeeRepository.save(employee);
        return EmployeeMapper.MAPPER.toDto(upadteEmployee);
    }
}
