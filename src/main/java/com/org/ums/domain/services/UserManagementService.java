package com.org.ums.domain.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.org.ums.entity.model.User;
import com.org.ums.repository.UserRepository;

@Service
public class UserManagementService  implements IUserManagementService{
	
	@Autowired UserRepository userRepository;
	
	@Autowired BCryptPasswordEncoder bCryptPasswordEncoder ;
	
	public User registerUser(User user) throws Exception{
		
		Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
		if(existingUser.isPresent()){
			throw new Exception("User already exist");
		}
		
		User adminUser = new User();
		adminUser.setActive(false);
		adminUser.setEmail(user.getEmail());
		adminUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
		User savedUser = userRepository.save(adminUser);
		savedUser.setPassword("");
		
		return savedUser;
	}

	@Override
	public void deleteUser(String email) {
		userRepository.deleteByEmail(email);
	}

	@Override
	public User updateUser(User user) throws Exception {
		Optional<User> existingUser =  userRepository.findByEmail(user.getEmail());
		if(!existingUser.isPresent())  throw new Exception("User Not found");
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
		existingUser.ifPresent((updateUser) ->user.setUserId(updateUser.getUserId()));
		
		userRepository.saveAndFlush(user);
		
		user.setPassword("");
		return user;
	}


}
