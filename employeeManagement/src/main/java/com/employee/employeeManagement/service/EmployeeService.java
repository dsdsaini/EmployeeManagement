package com.employee.employeeManagement.service;

import com.employee.employeeManagement.entity.Employee;
import com.employee.employeeManagement.exception.EmployeeNotFoundByDeptException;
import com.employee.employeeManagement.exception.EmployeeNotFoundException;
import com.employee.employeeManagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

// Uncomment this
// comment out the in-memory service (EmployeeServiceForInMemory), if you want to use the H2 database service
//@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees(String department) {
        return department == null ? employeeRepository.findAll() : employeeRepository.findByDepartmentIgnoreCase(department);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    // This is for getting employees by department (Optional Method)
    public List<Employee> getEmployeesByDepartment(String department) {
        List<Employee> employees = employeeRepository.findByDepartmentIgnoreCase(department);
        if (employees.isEmpty()) {
            throw new EmployeeNotFoundByDeptException(department);
        }
        return employees;
    }

    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee employee = getEmployeeById(id);
        if (employee == null) {
            throw new EmployeeNotFoundException(id);
        }
        employee.setName(updatedEmployee.getName());
        employee.setDepartment(updatedEmployee.getDepartment());
        employee.setSalary(updatedEmployee.getSalary());
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException(id);
        }
        employeeRepository.deleteById(id);
    }
}

