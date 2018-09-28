package com.org.ums.domain.services;

import java.util.List;

import com.org.ums.entity.model.Role;
import com.org.ums.entity.model.User;
import com.org.ums.model.UserRoleMappingBean;

public interface IUserRoleManagementService {
	void mapRole(UserRoleMappingBean userRoleMappingBean) throws Exception;
}
