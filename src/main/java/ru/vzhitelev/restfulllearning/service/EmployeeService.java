package ru.vzhitelev.restfulllearning.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.vzhitelev.restfulllearning.dto.EmployeeDTO;
import ru.vzhitelev.restfulllearning.entity.Department;
import ru.vzhitelev.restfulllearning.entity.Employee;
import ru.vzhitelev.restfulllearning.map.EmployeeMapper;
import ru.vzhitelev.restfulllearning.repository.DepartmentRepository;
import ru.vzhitelev.restfulllearning.repository.EmployeeRepository;
import ru.vzhitelev.restfulllearning.response.RestApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<Employee> list() {
        return employeeRepository.findByOrderById();
    }

    public List<EmployeeDTO> queryList(Long departmentId, String msisdn) {
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        if (departmentId == null && msisdn == null) {
            employeeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                    .forEach(
                            employee -> employeeDTOList.add(EmployeeMapper.mapToEmployeeDto(employee))
                    );
            return employeeDTOList;
        }
        if (departmentId == null) {
            employeeRepository.findEmployeeByMSISDN(msisdn)
                    .stream()
                    .toList()
                    .forEach(
                            employee -> employeeDTOList.add(EmployeeMapper.mapToEmployeeDto(employee))
                    );
            return employeeDTOList;
        }
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RestApiException("Employee with id: " + departmentId + " not found"));
        if (msisdn == null) {
            employeeRepository.findEmployeeByDepartmentId(department)
                    .forEach(
                            employee -> employeeDTOList.add(EmployeeMapper.mapToEmployeeDto(employee))
                    );
            return employeeDTOList;
        } else {
            employeeRepository.findEmployeeByMSISDNAndDepartmentId(msisdn, department)
                    .forEach(
                            employee -> employeeDTOList.add(EmployeeMapper.mapToEmployeeDto(employee))
                    );
            return employeeDTOList;
        }
    }

    public EmployeeDTO getById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RestApiException("Employee with id: " + id + " not found"));
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    public EmployeeDTO addEmployee(Employee employee) {
        if (employeeRepository.findEmployeeByMSISDN(employee.getMSISDN()).isPresent())
            throw new RestApiException("MSISDN is busy");
        return EmployeeMapper.mapToEmployeeDto(employeeRepository.save(employee));
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public EmployeeDTO updateEmployee(Employee employee) {
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
        return EmployeeMapper.mapToEmployeeDto(updateEmployee);
    }

    public EmployeeDTO removeDepartment(@NotNull Long id) {
        Employee employee = employeeRepository.findById(id).
                orElseThrow(() -> new RestApiException("Employee with id: " + id + " not found"));
        employee.setDepartmentId(null);
//                departmentRepository.findById(-1L).
//                orElseThrow(()-> new RestApiException("Department id: -1 not found")));
        Employee updateEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(updateEmployee);
    }

    public EmployeeDTO setDepartment(@NotNull Long id, @NotNull Department department) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RestApiException("Employee with id: " + id + " not found"));
        employee.setDepartmentId(departmentRepository.findById(department.getId())
                .orElseThrow(()-> new RestApiException(String.format("Department with id: %d not found", department.getId()))));
        Employee upadteEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(upadteEmployee);
    }
}
