package com.tandp.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="jobApplication")
public class JobApplication {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long jobapplid;
	private int status;
	@ManyToOne(cascade = CascadeType.ALL)
	private JobPost post;
	@OneToOne
	private Student student;
	
	public long getJobapplid() {
		return jobapplid;
	}
	public void setJobapplid(long jobapplid) {
		this.jobapplid = jobapplid;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public JobPost getPost() {
		return post;
	}
	public void setPost(JobPost post) {
		this.post = post;
	}
	public JobApplication() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
