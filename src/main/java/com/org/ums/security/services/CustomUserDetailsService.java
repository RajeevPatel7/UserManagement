package com.org.ums.security.services;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.org.ums.entity.model.User;
import com.org.ums.model.CustomUserDetails;
import com.org.ums.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<User> optionalUser = userRepository.findByEmailId(email);
		
		optionalUser.
			orElseThrow(() -> new UsernameNotFoundException("User is not avaialble"));
		
		CustomUserDetails userDetails = optionalUser
			.map(CustomUserDetails::new).get();
		
		return userDetails;
	}

}
