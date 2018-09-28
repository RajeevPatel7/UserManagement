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

import com.org.ums.domain.services.RoleManagementService;
import com.org.ums.domain.services.UserManagementService;
import com.org.ums.domain.services.UserRoleManagementService;
import com.org.ums.entity.model.Role;
import com.org.ums.entity.model.User;
import com.org.ums.model.UserRoleMappingBean;

@EnableJpaRepositories(basePackages = "com.org.ums.repository")
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired 	private UserDetailsService userDetailsService;
	@Autowired	UserManagementService createUserService;
	@Autowired	RoleManagementService roleManagementService;
	@Autowired UserRoleManagementService userRoleMgmtSvc;

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		initialize();
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
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {

		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

		return bCryptPasswordEncoder;

	}
	
	//TODO
	private void initialize() throws Exception {

		User user = new User();
		user.setEmail("admin@hcl.com");
		user.setPassword("pwd");
		createUserService.registerUser(user);

		Role role = new Role();
		role.setRole("Admin");
		roleManagementService.registerRole(role);

		UserRoleMappingBean userRoleMappingBean = new UserRoleMappingBean();
		userRoleMappingBean.setUserId("admin@hcl.com");
		userRoleMappingBean.setRoleList(Arrays.asList(("Admin")));
		userRoleMgmtSvc.mapRole(userRoleMappingBean);
	}

}
