package com.example.employeemanagement.dto;

import java.math.BigDecimal;

public record EmployeeResponse(Long id,
                               String name,
                               String email,
                               String department,
                               BigDecimal salary) {
}
