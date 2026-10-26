package com.example.employeemanagement.exception;

public class EmployeeAlreadyExistsException extends RuntimeException{

    public EmployeeAlreadyExistsException(String email) {
        super("An employee with email " + email + " already exists");
    }
}
