package com.employee.employeeManagement.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<String> handleNotFound(EmployeeNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(EmployeeNotFoundByDeptException.class)
    public ResponseEntity<String> handleNotFoundByDept(EmployeeNotFoundByDeptException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }
}

