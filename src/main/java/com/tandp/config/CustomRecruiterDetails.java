package com.tandp.config;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tandp.entities.Recruiter;

public class CustomRecruiterDetails implements UserDetails{

	
	private Recruiter recruiter;
	
	
	
	public CustomRecruiterDetails(Recruiter recruiter) {
		super();
		this.recruiter = recruiter;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(recruiter.getRole());
		return Arrays.asList(simpleGrantedAuthority);
	
	}

	@Override
	public String getPassword() {
		
		return recruiter.getCompanypassword();
	}

	@Override
	public String getUsername() {
		
		return recruiter.getCompanyemail();
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}

}
