package com.org.ums.domain.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.ums.entity.model.Role;
import com.org.ums.repository.RoleRepository;

@Service
public class RoleManagementService implements IRoleManagementService {
	
	@Autowired RoleRepository roleRepository;

	@Override
	public Role registerRole(Role role) throws Exception {
		Optional<Role> existingRole = roleRepository.findByRole(role.getRole());
		
		if(existingRole.isPresent()){
			throw new Exception("Duplicate role");
		}
		
		return roleRepository.save(role);
	}

	@Override
	public void deleteRole(String role) {
		roleRepository.deleteByRole(role);
	}

}
