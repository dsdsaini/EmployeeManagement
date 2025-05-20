package com.employee.employeeManagement.service;

import com.employee.employeeManagement.entity.Employee;
import com.employee.employeeManagement.exception.EmployeeNotFoundByDeptException;
import com.employee.employeeManagement.exception.EmployeeNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceForInMemory {

    private final Map<Long, Employee> employees = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong();

    public Employee saveEmployee(Employee employee) {
        long newId = idCounter.incrementAndGet();
        employee.setId(newId);
        employees.put(newId, employee);
        return employee;
    }

    public List<Employee> getAllEmployees(String department) {
        List<Employee> employeeList = new ArrayList<>(employees.values());
        if (department != null && !department.isEmpty()) {
            return employeeList.stream()
                    .filter(emp -> department.equalsIgnoreCase(emp.getDepartment()))
                    .collect(Collectors.toList());
        }
        return employeeList;
    }

    public Employee getEmployeeById(Long id) {
        Employee employee = employees.get(id);
        if (employee == null) {
            throw new EmployeeNotFoundException(id);
        }
        return employee;
    }

    public List<Employee> getEmployeesByDepartment(String department) {
        List<Employee> employeeList = new ArrayList<>(employees.values());
        List<Employee> filteredEmployees = employeeList.stream()
                .filter(emp -> department.equalsIgnoreCase(emp.getDepartment()))
                .collect(Collectors.toList());
        if (filteredEmployees.isEmpty()) {
            throw new EmployeeNotFoundByDeptException(department);
        }
        return filteredEmployees;
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = employees.get(id);
        if (employee == null) {
            throw new EmployeeNotFoundException(id);
        }
        employee.setName(employeeDetails.getName());
        employee.setDepartment(employeeDetails.getDepartment());
        employee.setSalary(employeeDetails.getSalary());
        employees.put(id, employee); // replace the old one
        return employee;
    }

    public void deleteEmployee(Long id) {
        if (!employees.containsKey(id)) {
            throw new EmployeeNotFoundException(id);
        }
        employees.remove(id);
    }
}
