package com.tandp.entities;

import java.util.List;
import java.util.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="Recruiter")
public class Recruiter {

	@Id
	private long cid;
	private String companyname;
	@Column(unique=true)
	private String companyemail;
	private String companypassword;
	private String role;
	private String city;
	private String addr;
	@Column(unique=true)
	private String web;
	private String contact_no;
	@OneToMany(cascade =CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="recruiter")
	private List<JobPost> jobpost=new ArrayList<>();
	public long getCid() {
		return cid;
	}
	public void setCid(long cid) {
		this.cid = cid;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getWeb() {
		return web;
	}
	public void setWeb(String web) {
		this.web = web;
	}
	public String getContact_no() {
		return contact_no;
	}
	public void setContact_no(String contact_no) {
		this.contact_no = contact_no;
	}
	public List<JobPost> getJobPost() {
		return jobpost;
	}
	public void setJobPost(List<JobPost> jobPost) {
		jobpost = jobPost;
	}
	public String getCompanyemail() {
		return companyemail;
	}
	public void setCompanyemail(String companyemail) {
		this.companyemail = companyemail;
	}
	public String getCompanypassword() {
		return companypassword;
	}
	public void setCompanypassword(String companypassword) {
		this.companypassword = companypassword;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
	
	
	
	
	
}
