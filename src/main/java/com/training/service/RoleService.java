package com.training.service;

import java.util.Optional;

import com.training.model.Role;

public interface RoleService {
	Optional<Role> findByRoleName(String name);
}
