package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.EmployeeRequest;
import com.example.employeemanagement.dto.EmployeeResponse;
import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.exception.EmployeeAlreadyExistsException;
import com.example.employeemanagement.exception.EmployeeNotFoundException;
import com.example.employeemanagement.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    private static final Set<String> ALLOWED_SORT_FIELDS =
            Set.of("id", "name", "email", "department", "salary");


    @Override
    public EmployeeResponse createEmployee(EmployeeRequest request) {

        if (employeeRepository.existsByEmail(request.getEmail())){
            throw new EmployeeAlreadyExistsException(request.getEmail());
        }

        Employee employee = new Employee();

        toEntity(request, employee);

        Employee savedEmployee = employeeRepository.save(employee);

        return toResponse(savedEmployee);
    }

    @Override
    public Page<EmployeeResponse> getAllEmployees(int page, int size, String sortBy, String direction) {

        if (page < 0){
            throw new IllegalArgumentException("Page number cannot be negative");
        }

        if (size < 1 || size > 100){
            throw new IllegalArgumentException(
                    "Page size must be between 1 and 100");
        }

        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new IllegalArgumentException(
                    "Invalid sort field. Allowed fields: "
                            + ALLOWED_SORT_FIELDS);
        }

        Sort.Direction sortDirection;

        try {
            sortDirection = Sort.Direction.fromString(direction);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(
                    "Direction must be 'asc' or 'desc'");
        }

        Pageable pageable = PageRequest.of(
                page, size, Sort.by(sortDirection, sortBy));

        return employeeRepository.findAll(pageable)
                .map(this::toResponse);
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = findEmployeeById(id);

        return toResponse(employee);
    }

    @Override
    @Transactional
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
        Employee employee = findEmployeeById(id);

        if (employeeRepository.existsByEmailAndIdNot(
                request.getEmail(), id)) {
            throw new EmployeeAlreadyExistsException(request.getEmail());
        }

        toEntity(request, employee);

        Employee updatedEmployee = employeeRepository.save(employee);

        return toResponse(updatedEmployee);
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        Employee employee = findEmployeeById(id);

        employeeRepository.delete(employee);
    }

    private Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    private void toEntity(
            EmployeeRequest request, Employee employee) {

        employee.setName(request.getName().trim());
        employee.setEmail(request.getEmail().trim());
        employee.setDepartment(request.getDepartment().trim());
        employee.setSalary(request.getSalary());
    }

    private EmployeeResponse toResponse(Employee employee) {

        return new EmployeeResponse(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getDepartment(),
                employee.getSalary()
        );
    }
}
