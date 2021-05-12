package com.training.serviceImpl;

import com.training.dto.EmployeeDto;
import com.training.model.Employee;
import com.training.model.Role;
import com.training.repository.EmployeeRepository;
import com.training.service.EmployeeService;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Override
    public Employee save(EmployeeDto employeeDto) {
    	Optional<Employee> employee = employeeRepository.findById(employeeDto.getEmployeeId());
    	if (!employee.isPresent()) {
    		return employeeRepository.save(modelMapper.map(employeeDto, Employee.class));
    	}
    	Employee employeeUpdate = modelMapper.map(employeeDto, Employee.class);
    	employeeUpdate.setUserName(employee.get().getUserName());
    	employeeUpdate.setPassword(employee.get().getPassword());
    	List<Role> role = new ArrayList<>();
    	employee.get().getRoles().forEach(e -> role.add(e));
    	employeeUpdate.setRoles(role);
        return employeeRepository.save(employeeUpdate);
    }

    @Override
    public Optional<EmployeeDto> findById(String employeeId) {
    	return employeeRepository.findById(employeeId)
    			.map(employee -> modelMapper.map(employee, EmployeeDto.class));
    }

	@Override
	public List<EmployeeDto> findAll() {
		return employeeRepository.findAllLogic().stream()
		.map(employee -> modelMapper.map(employee, EmployeeDto.class))
		.collect(Collectors.toList());
	}

	@Override
	public void deleteById(String employeeId) {
		employeeRepository.deleteById(employeeId);
	}

	@Override
	public Page<EmployeeDto> findPaginated(int page, int size, String sortField, String sortDir, String keyword) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(page -1, size, sort);
        if (keyword.isEmpty()) {
        	return (Page<EmployeeDto>) employeeRepository.findAllLogic(pageable)
					.map(employee -> modelMapper.map(employee, EmployeeDto.class));
        }
        return (Page<EmployeeDto>) employeeRepository.findByKeyword(keyword, pageable)
        		.map(employee -> modelMapper.map(employee, EmployeeDto.class));
	}

}
