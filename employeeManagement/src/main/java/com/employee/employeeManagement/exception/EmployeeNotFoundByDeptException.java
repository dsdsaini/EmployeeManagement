package com.employee.employeeManagement.exception;

public class EmployeeNotFoundByDeptException extends RuntimeException {
    public EmployeeNotFoundByDeptException(String department) {
        super("Employee not found with department: " + department);
    }
}
