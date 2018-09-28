package com.org.ums.domain.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.org.ums.entity.model.User;
import com.org.ums.model.UserRoleMappingBean;
import com.org.ums.repository.UserRepository;

@Service
public class UserManagementService  implements IUserManagementService{
	
	@Autowired UserRepository userRepository;
	
	@Autowired UserRoleManagementService userRoleMgmtSvc;
	
	@Autowired BCryptPasswordEncoder bCryptPasswordEncoder ;
	
	public User registerUser(User user) throws Exception{
		
		Optional<User> existingUser = userRepository.findByEmailId(user.getEmailId());
		if(existingUser.isPresent()){
			throw new Exception("User already exist");
		}
		
		User adminUser = new User();
		adminUser.setActive(false);
		adminUser.setEmailId(user.getEmailId());
		adminUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
		User savedUser = userRepository.save(adminUser);
		savedUser.setPassword("");
		savedUser.setUserId(-1);
		return savedUser;
	}

	@Override
	public void deleteUser(String email) throws Exception {
		try{
			Optional<User> user = userRepository.findByEmailId(email);
			if(!user.isPresent()) return;
			user.get().setRoles(null);
			updateUser(user.get());
			userRepository.deleteByEmailId(email);	
		}catch(Exception e){
			throw new Exception("remove user and role mapping");
		}
	}

	@Override
	public User updateUser(User user) throws Exception {
		Optional<User> existingUser =  userRepository.findByEmailId(user.getEmailId());
		if(!existingUser.isPresent())  throw new Exception("User Not found");
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
		existingUser.ifPresent((updateUser) ->user.setUserId(updateUser.getUserId()));
		
		userRepository.saveAndFlush(user);
		
		user.setPassword("");
		return user;
	}

	@Override
	public List<UserRoleMappingBean> getUserList() {
		return userRepository.findAll().stream().map((user) ->{
			UserRoleMappingBean bean = new UserRoleMappingBean();
			bean.setEmailId(user.getEmailId());
			bean.setActive(user.isActive());
			bean.setRoleList(user.getRoles().stream().map(role->role.getRole()).collect(Collectors.toList()));
			return bean;
		}).collect(Collectors.toList());
	}


}
