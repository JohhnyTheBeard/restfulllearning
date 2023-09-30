package ru.vzhitelev.restfulllearning.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.vzhitelev.restfulllearning.dto.EmployeeDto;
import ru.vzhitelev.restfulllearning.entity.Department;
import ru.vzhitelev.restfulllearning.entity.Employee;
import ru.vzhitelev.restfulllearning.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/v1/employees")
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    private List<EmployeeDto> list(
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) String msisdn
    ) {
        return employeeService.queryList(departmentId, msisdn);
    }

    @GetMapping("{id}")
    private EmployeeDto getById(@PathVariable Long id) {
        return employeeService.getById(id);
    }

    @PostMapping
    private EmployeeDto add(@RequestBody EmployeeDto employeeDto) {
        return employeeService.addEmployee(employeeDto);
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }

    @PutMapping
    private EmployeeDto update(@RequestBody EmployeeDto employeeDto) {
        return employeeService.updateEmployee(employeeDto);
    }

    @PatchMapping("/{id}/remove-department")
    private EmployeeDto removeDepartment(@PathVariable Long id) {
        return employeeService.removeDepartment(id);
    }

    @PatchMapping("/{id}/set-department")
    public EmployeeDto setDepartment(@PathVariable Long id, @RequestBody Department department) {
        return employeeService.setDepartment(id, department);
    }
}
