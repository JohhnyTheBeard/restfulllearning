package ru.vzhitelev.restfulllearning.controller;

import org.springframework.web.bind.annotation.*;
import ru.vzhitelev.restfulllearning.dto.EmployeeDTO;
import ru.vzhitelev.restfulllearning.entity.Department;
import ru.vzhitelev.restfulllearning.entity.Employee;
import ru.vzhitelev.restfulllearning.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/v1/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    private List<EmployeeDTO> list(
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) String msisdn
    ) {
        return employeeService.queryList(departmentId, msisdn);
    }

    @GetMapping("{id}")
    private EmployeeDTO getById(@PathVariable Long id) {
        return employeeService.getById(id);
    }

    @PostMapping
    private EmployeeDTO add(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }

    @PutMapping
    private EmployeeDTO update(@RequestBody Employee employee) {
        return employeeService.updateEmployee(employee);
    }

    @PatchMapping("/{id}/remove-department")
    private EmployeeDTO removeDepartment(@PathVariable Long id) {
        return employeeService.removeDepartment(id);
    }

    @PatchMapping("/{id}/set-department")
    public EmployeeDTO setDepartment(@PathVariable Long id, @RequestBody Department department) {
        return employeeService.setDepartment(id, department);
    }
}
