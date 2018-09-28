package com.org.ums.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.org.ums.domain.services.IUserRoleManagementService;
import com.org.ums.model.UserRoleMappingBean;

@RestController
public class UserRoleMappingController {

	@Autowired IUserRoleManagementService userRoleManagementService;
	
	@RequestMapping(value="userRoleMapping", method=RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE})
	public UserRoleMappingBean mapUserToRoles(@RequestBody UserRoleMappingBean userRoleMappingBean) throws Exception{
		return userRoleManagementService.mapRole(userRoleMappingBean);
	}
}
