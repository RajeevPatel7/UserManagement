package com.org.ums.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.org.ums.domain.services.IUserManagementService;
import com.org.ums.entity.model.User;
import com.org.ums.model.UserRoleMappingBean;

@RestController
public class UserManagementController {
	
	@Autowired IUserManagementService userManagementService;
	
	@RequestMapping(value ="/user", method=RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE})
	public User registerUser(@RequestBody User user) throws Exception{
		return userManagementService.registerUser(user);
	}
	
	@RequestMapping(value ="/deleteUser/{userName}", method=RequestMethod.DELETE)
	public void deleteUser(@PathVariable("userName") String email) throws Exception{
		 userManagementService.deleteUser(email);
	}
	
	@RequestMapping(value="updateUser", method=RequestMethod.PUT, produces={MediaType.APPLICATION_JSON_VALUE})
	public User updateUser(@RequestBody User user) throws Exception{
		return userManagementService.updateUser(user);
	}
	
	@RequestMapping(value="/userList", method= RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE})
	public List<UserRoleMappingBean> getUserList(){
		return userManagementService.getUserList();
	}
}
