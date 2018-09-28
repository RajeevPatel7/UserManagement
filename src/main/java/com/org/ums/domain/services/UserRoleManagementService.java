package com.org.ums.domain.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.ums.entity.model.Role;
import com.org.ums.entity.model.User;
import com.org.ums.model.UserRoleMappingBean;
import com.org.ums.repository.RoleRepository;
import com.org.ums.repository.UserRepository;

@Service
public class UserRoleManagementService implements IUserRoleManagementService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	public UserRoleMappingBean mapRole(UserRoleMappingBean userRoleMappingBean) throws Exception {
		
		List<Role> dbRoleList = roleRepository.findAll();
		
		List<String> stringifyRoles = dbRoleList.stream().map(Role::getRole).collect(Collectors.toList());
		
		if (stringifyRoles.containsAll(userRoleMappingBean.getRoleList())) {
			return mapUsersToRoles(userRoleMappingBean);
		} else {
			throw new Exception("Few Roles are not avaialble");
		}
	}

	private UserRoleMappingBean mapUsersToRoles(UserRoleMappingBean userRoleMappingBean) throws Exception {
	
		Optional<User> dbUser = userRepository.findByEmailId(userRoleMappingBean.getEmailId());
		
		if (!dbUser.isPresent()) {
			throw new Exception("Wrong users");
		}
		List<Role> roleList = new ArrayList<>();
		
		for(String role : userRoleMappingBean.getRoleList()){
			roleList.add(roleRepository.findByRole(role).get());
		}
		
		dbUser.get().setRoles(roleList);
		dbUser.get().setActive(true);
		userRepository.save(dbUser.get());
		userRoleMappingBean.setActive(true);
		return userRoleMappingBean;

	}
}
