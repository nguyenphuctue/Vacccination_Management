package com.training.service;

import com.training.dto.EmployeeDto;
import com.training.model.Employee;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

public interface EmployeeService {
	List<EmployeeDto> findAll();
    Employee save(EmployeeDto employeeDto);
    Optional<EmployeeDto> findById(String employeeId);
    void deleteById(String employeeId);
    Page<EmployeeDto> findPaginated(int page, int size, String sortField, String sortDir, String keyword);
    
}
