package com.tandp.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tandp.entities.Student;

  public interface StudentRepository extends JpaRepository<Student,Integer>{
  
  @Query("select st from Student st where st.email = : email") 
  public Student getStudentByEmail(@Param("email") String email);
  
  
  public Student findByEmail(String email);
  
  }
 