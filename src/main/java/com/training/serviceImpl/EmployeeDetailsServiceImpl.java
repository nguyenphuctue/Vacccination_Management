package com.training.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.training.model.EmployeeDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.training.model.Employee;
import com.training.model.Role;
import com.training.repository.EmployeeRepository;

@Service
@RequiredArgsConstructor
public class EmployeeDetailsServiceImpl implements UserDetailsService {

	private final EmployeeRepository employeeRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Employee> optionalEmployee = employeeRepository.findByUserName(username);
		if(optionalEmployee.isPresent()) {
			Employee employee = optionalEmployee.get();
			List<GrantedAuthority> authorities = new ArrayList<>();
			List<Role> roles = employee.getRoles();
			roles.stream().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName())));
			EmployeeDetail employeeDetail = new EmployeeDetail(employee, authorities);
			return (UserDetails) employeeDetail;
		}
		throw  new UsernameNotFoundException("Cannot found User");
	}

}
