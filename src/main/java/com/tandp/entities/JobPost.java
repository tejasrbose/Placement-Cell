package com.tandp.entities;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.*;
@Entity
@Table(name="Jobpost")
public class JobPost {
    
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int jobid;
	@ManyToOne
	private Recruiter recruiter;
    private String skills;
    private String desgn;
    private int pkg;
    private String minqauli;
    private Date date;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobApplication> jobApplications = new ArrayList<>();
    public JobPost() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Recruiter getRecruiter() {
		return recruiter;
	}
	public void setRecruiter(Recruiter recruiter) {
		this.recruiter = recruiter;
	}
	public String getSkills() {
		return skills;
	}
	public void setSkills(String skills) {
		this.skills = skills;
	}
	
	public String getDesgn() {
		return desgn;
	}
	public void setDesgn(String desgn) {
		this.desgn = desgn;
	}
	public int getPkg() {
		return pkg;
	}
	public void setPkg(int pkg) {
		this.pkg = pkg;
	}
	public List<JobApplication> getJobApplications() {
		return jobApplications;
	}
	public void setJobApplications(List<JobApplication> jobApplications) {
		this.jobApplications = jobApplications;
	}
	public String getMinqauli() {
		return minqauli;
	}
	public void setMinqauli(String minqauli) {
		this.minqauli = minqauli;
	}
	public int getJobid() {
		return jobid;
	}
	public void setJobid(int jobid) {
		this.jobid = jobid;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
    
}
