package com.tandp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.tandp.Dao.StudentRepository;
import com.tandp.entities.Student;

public class StudentDetailsServiceImple implements UserDetailsService{

	
	@Autowired
      private	StudentRepository studentRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Student student=this.studentRepository.findByEmail(username);
		
		
		
		if(student==null)
		{
			throw new UsernameNotFoundException("user not available");
		}
		return new CustomStudentDetails(student);
	}

}
