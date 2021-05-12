package com.training.serviceImpl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.training.model.Role;
import com.training.repository.RoleRepository;
import com.training.service.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
	
	private final RoleRepository rolerepository;

	@Override
	public Optional<Role> findByRoleName(String name) {
		return rolerepository.findByRoleName(name);
	}

}
