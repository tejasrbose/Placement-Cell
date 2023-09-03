package com.tandp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.tandp.Dao.RecruiterRepository;
/* import com.tandp.Dao.RecruiterRepository; */
import com.tandp.entities.Recruiter;



  public class RecruiterDetailsServiceImpl implements UserDetailsService{
  
  
  @Autowired private RecruiterRepository recruiterRepository;
  
  @Override public UserDetails loadUserByUsername(String username) throws
  UsernameNotFoundException {
  
  Recruiter recruiter=this.recruiterRepository.findByCompanyemail(username);
  
  if(recruiter==null) { throw new
  UsernameNotFoundException("user not available"); } return new
  CustomRecruiterDetails(recruiter);
  
  
  }
 
  }
 