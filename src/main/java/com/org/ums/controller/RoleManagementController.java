package com.org.ums.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.org.ums.domain.services.IRoleManagementService;
import com.org.ums.entity.model.Role;

@RestController
public class RoleManagementController {
	
	@Autowired IRoleManagementService roleManagemetnService;
	@RequestMapping(value ="/createRole", method=RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE})
	public Role createRole(@RequestBody Role role) throws Exception{
		return roleManagemetnService.registerRole(role);
	}
	
	@RequestMapping(value ="/deleteRole/{roleName}", method=RequestMethod.DELETE)
	public void deleteRole(@PathVariable("roleName") String roleName){
		roleManagemetnService.deleteRole(roleName);
	}
	
}
