package com.tandp.controllers;


import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tandp.helper.JobService;
import com.tandp.helper.Messages;
import com.tandp.entities.JobPost;
import com.tandp.entities.Recruiter;
import com.tandp.Dao.JobApplicationRepository;
import com.tandp.Dao.JobpostRepository;
import com.tandp.Dao.RecruiterRepository;
import com.tandp.Dao.StudentRepository;
import com.tandp.entities.Student;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class MyControllers {

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private RecruiterRepository recruiterRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JobApplicationRepository jobAppicationRepository;
	
	@Autowired
	private JobpostRepository jobpostRepository;
	
     @Autowired
	private JobService jobService ;
	
	
	@PostMapping("/stprocess")
	public String processStudent(@Valid @ModelAttribute("student") Student student,Model model,HttpSession session,BindingResult result)
	{
		try {
		
			
			if(result.hasErrors())
			{
				return "Student/stregstr";
			}
			else {	
			
				student.setPassword(this.passwordEncoder.encode(student.getPassword()));
				student.setRole("ROLE_STUDENT");
				this.studentRepository.save(student);
				System.out.println(student);
				model.addAttribute("student",new Student());
				session.setAttribute("message",new Messages("Succesfully register !","alert-success"));
				return "Student/stregstr";
		}
			
		}catch(Exception e)
		{
			e.printStackTrace();
			model.addAttribute("student",new Student());
		}
		
		System.out.println(student);
		return "Main";
	}
	
	@PostMapping("/reprocess")
	public String processRecruiter(@Valid @ModelAttribute("recruiter") Recruiter recruiter,Model model,HttpSession session,BindingResult result)
	{
	try {		
			
			if(result.hasErrors())
				return "Recruiter/recruiter";

			recruiter.setCompanypassword(this.passwordEncoder.encode(recruiter.getCompanypassword()));
			recruiter.setRole("ROLE_RECRUITER");
			this.recruiterRepository.save(recruiter);
			System.out.println(recruiter);
			model.addAttribute("recruiter",new Recruiter());
			session.setAttribute("message",new Messages("Succesfully register !","alert-success"));
			return "redirect:/mainpage";
			
		}catch(Exception e)
		{
			e.printStackTrace();
			model.addAttribute("recruiter",new Recruiter());
		}
		
		
	return "redirect:/mainpage";
	}
	
	
	@GetMapping("/loginme")
	public String loginme(Model model)
	{
		model.addAttribute("student",new Student());
		
		return "mylogin";
	}
	
	
	
	@GetMapping("/mainpage")
	public String mainpage()
	{
		return "Main";
	}
	
	@GetMapping("/logadmin")
	public String logadmin()
	{
		return "Admin/log";
	}
	
	@GetMapping("/registerstudent")
	public String studentreg(Model model)
	{
		model.addAttribute("student",new Student());
		return "Student/stregstr";
	}
	
	@GetMapping("/registerrecruiter")
	public String recruitorreg(Model model)
	{
		model.addAttribute("recruiter",new Recruiter());
		return "Recruiter/recruiter";
	}
	
	@GetMapping("/Recruiterlogin")
	public String loginrecruiter(Model model)
	{
		model.addAttribute("recruiter",new Recruiter());
		
		return "recruiterlogin";
	}
	
	@GetMapping("/stindex")
	public String loginme1(Model model)
	{
		model.addAttribute("student",new Student());
		
		return "Role_Based_index";
	}
	
	@GetMapping("/choose")
	public String choosereg() {
		return "chooseregister";
	}
	
	@GetMapping("/logout")
	public String logout()
	{
		
		return "Main";
	}
	
	@GetMapping("/Viewjobdesc/{jobid}")
	public String viewJob(Model model,@PathVariable int jobid)
	{
		Optional<JobPost> jp=this.jobpostRepository.findById(jobid);
		JobPost j=jp.get();
		model.addAttribute("jp", j);
		return "Student/viewjobdesc";
	}
	
	/*
	 * @GetMapping("/Remove/{jobId}") public String deleteJobPost(@PathVariable int
	 * jobId) { Optional<JobPost> jobPost = this.jobpostRepository.findById(jobId);
	 * JobPost jp=jobPost.get();
	 * 
	 * this.jobAppicationRepository.deleteAll(jp.getJobApplications());
	 * this.jobpostRepository.delete(jp); return "redirect:recruiter/postjob";
	 * 
	 * }
	 */
	 @GetMapping("/Remove/{jobId}")
	public String deleteJob(@PathVariable int jobId) {
        try {
        	 jobService.deleteJobPostWithReferences(jobId);
             return "redirect:/recruiter/postjob";
        } catch (Exception e) {
             return "redirect:/recruiter/postjob";
        }
    }
	 
	 @GetMapping("/land")
	 public String  viewMe()
	 {
		 return "Land";
	 }
	
	 
	
	
}
