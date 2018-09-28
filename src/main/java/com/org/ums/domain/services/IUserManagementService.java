package com.org.ums.domain.services;

import com.org.ums.entity.model.User;

public interface IUserManagementService {
	User registerUser(User user) throws Exception;
	void deleteUser(String email);
	User updateUser(User user) throws Exception;

}
