package com.tandp.entities;

import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="ADMIN")
public class Admin {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String email;
	private String password;
	
	@OneToMany
	private List<Student> students=new ArrayList<>();
	@OneToMany
	private List<JobPost> jobpost=new ArrayList<>();
	private String Notice;
	private List<String> placeRecord;
	
	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public List<JobPost> getJobpost() {
		return jobpost;
	}

	public void setJobpost(List<JobPost> jobpost) {
		this.jobpost = jobpost;
	}

	public String getNotice() {
		return Notice;
	}

	public void setNotice(String notice) {
		Notice = notice;
	}

	public List<String> getPlaceRecord() {
		return placeRecord;
	}

	public void setPlaceRecord(List<String> placeRecord) {
		this.placeRecord = placeRecord;
	}
	
	
	
	
}
