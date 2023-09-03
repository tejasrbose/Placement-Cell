package com.tandp.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tandp.entities.JobApplication;


  public interface JobApplicationRepository extends JpaRepository<JobApplication,Integer>{
  
	  @Query("SELECT ja FROM JobApplication ja WHERE ja.post.recruiter.cid = :recruiterId")
	    List<JobApplication> findByRecruiterId(@Param("recruiterId") Long recruiterId);
  }
 