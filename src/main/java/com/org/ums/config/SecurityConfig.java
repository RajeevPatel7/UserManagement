package com.org.ums.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.org.ums.domain.services.RoleManagementService;
import com.org.ums.domain.services.UserManagementService;
import com.org.ums.domain.services.UserRoleManagementService;
import com.org.ums.entity.model.Role;
import com.org.ums.entity.model.User;
import com.org.ums.model.UserRoleMappingBean;

@EnableJpaRepositories(basePackages = "com.org.ums.repository")
@Configuration
@Component
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired 	private UserDetailsService userDetailsService;
	@Autowired	UserManagementService createUserService;
	@Autowired	RoleManagementService roleManagementService;
	@Autowired UserRoleManagementService userRoleMgmtSvc;

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {		
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic()
				.and().authorizeRequests()
				.antMatchers("/user").permitAll()
				.antMatchers("/createRole").hasRole("Admin")
				.antMatchers("/deleteRole/*").hasRole("Admin")
				.antMatchers("/*").authenticated()
				.and().formLogin();
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);

		http.csrf().disable();
		http.headers().frameOptions().disable();
		initialize();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {

		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

		return bCryptPasswordEncoder;

	}
	
	//TODO
	private void initialize() {

		User user = new User();
		user.setEmailId("admin@hcl.com");
		user.setPassword("admin");
		Role role = new Role();
		role.setRole("Admin");
		UserRoleMappingBean userRoleMappingBean = new UserRoleMappingBean();
		userRoleMappingBean.setEmailId("admin@hcl.com");
		userRoleMappingBean.setRoleList(Arrays.asList(("Admin")));
		try {
			createUserService.registerUser(user);
			roleManagementService.registerRole(role);
			
			userRoleMgmtSvc.mapRole(userRoleMappingBean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
