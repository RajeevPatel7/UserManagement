package com.org.ums.domain.services;

import java.util.List;

import com.org.ums.entity.model.User;
import com.org.ums.model.UserRoleMappingBean;

public interface IUserManagementService {
	User registerUser(User user) throws Exception;
	void deleteUser(String email) throws Exception;
	User updateUser(User user) throws Exception;
	List<UserRoleMappingBean> getUserList();

}
