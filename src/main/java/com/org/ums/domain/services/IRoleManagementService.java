package com.org.ums.domain.services;

import com.org.ums.entity.model.Role;

public interface IRoleManagementService {
	Role registerRole(Role role) throws Exception;
	void deleteRole(String role);
}
