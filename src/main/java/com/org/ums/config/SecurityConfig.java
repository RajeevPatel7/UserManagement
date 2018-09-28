package com.org.ums.config;

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

@EnableJpaRepositories(basePackages = "com.org.ums.repository")
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired 	private UserDetailsService userDetailsService;
	@Autowired	UserManagementService createUserService;
	@Autowired	RoleManagementService roleManagementService;

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
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {

		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

		return bCryptPasswordEncoder;

	}

}
