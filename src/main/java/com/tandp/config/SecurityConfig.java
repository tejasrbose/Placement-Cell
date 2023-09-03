package com.tandp.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.util.UrlPathHelper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@EnableWebSecurity
@Configuration
public class SecurityConfig{

	
	@Bean
	public UserDetailsService getUserDetailsService() {
		return new StudentDetailsServiceImple();
	}
	
	@Bean
	public UserDetailsService getRecruiterDetailsService() {
		return new RecruiterDetailsServiceImpl();
	}
	

	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider getDaoAuthProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());

		return daoAuthenticationProvider;
	}
	
	@Bean
	public DaoAuthenticationProvider getReAuthProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService( getRecruiterDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());

		return daoAuthenticationProvider;
	}
	
	@Bean
	public SecurityFilterChain filterchain(HttpSecurity http) throws Exception
	{
		http.csrf().disable()
		.authorizeHttpRequests().requestMatchers("/student/**").hasRole("STUDENT")
		.and().authorizeHttpRequests().requestMatchers("/admin/**").hasRole("ADMIN")
		.and().authorizeHttpRequests().requestMatchers("/recruiter/**").hasRole("RECRUITER")
		.and().authorizeHttpRequests().requestMatchers("/**").permitAll().and()
		.formLogin().and().logout().logoutSuccessHandler(new LogoutSuccessHandler()
				{

					@Override
					public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
							Authentication authentication) throws IOException, ServletException {
						// TODO Auto-generated method stub
						System.out.println("The user "+authentication.getName()+" has Logged out.");
						UrlPathHelper helper =new UrlPathHelper();
						String context=helper.getContextPath(request);
						response.sendRedirect(context + "/mainpage");
						
					}
				}).permitAll();
		
		http.authenticationProvider(getDaoAuthProvider());
		http.authenticationProvider(getReAuthProvider());
		
		return http.build();
		
	}
	
	
}
