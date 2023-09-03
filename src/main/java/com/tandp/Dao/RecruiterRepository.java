package com.tandp.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tandp.entities.Recruiter;
import com.tandp.entities.Student;


  public interface RecruiterRepository extends JpaRepository<Recruiter,Long> {
  
  @Query("select rcr from  Recruiter rcr where rcr.companyemail = : companyemail"
  ) public Recruiter getRecruiterByEmail(@Param("companyemail") String
  companyemail);
  
  public Recruiter findByCompanyemail(String Companyemail); 
  public Recruiter findByWeb(String Web); 
  }
 