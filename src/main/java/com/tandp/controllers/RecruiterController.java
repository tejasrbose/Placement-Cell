package com.tandp.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tandp.Dao.JobApplicationRepository;
import com.tandp.Dao.JobpostRepository;
import com.tandp.Dao.RecruiterRepository;
import com.tandp.Dao.StudentRepository;
import com.tandp.entities.JobApplication;
//import com.tandp.Dao.RecruiterRepository;
import com.tandp.entities.JobPost;
import com.tandp.entities.Recruiter;
import com.tandp.entities.Student;
import com.tandp.helper.Messages;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/recruiter")
public class RecruiterController {

	@Autowired
	private RecruiterRepository recruiterRepository;
	
	@Autowired
	private JobApplicationRepository jobApplicationRepository;
	
	@Autowired
	private JobpostRepository jobpostRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
    public RecruiterRepository getRecruiterRepository() {
		return recruiterRepository;
	}
	public void setRecruiterRepository(RecruiterRepository recruiterRepository) {
		this.recruiterRepository = recruiterRepository;	
		}
	public JobApplicationRepository getJobApplicationRepository() {
		return jobApplicationRepository;
	}
     
	
	public void setJobApplicationRepository(JobApplicationRepository jobApplicationRepository) {
		this.jobApplicationRepository = jobApplicationRepository;
	}
    
	public JobpostRepository getJobostRepository() {
		return jobpostRepository;
	}

	public void setJobostRepository(JobpostRepository jobpostRepository) {
		this.jobpostRepository = jobpostRepository;
	}

	@GetMapping("/updateme")
	public String updateprofile(Principal principle,Model model,@ModelAttribute("recruiter1") Recruiter recruiter1)
	{
       String username=principle.getName();
		System.out.println(username);
		Recruiter recruiter=this.recruiterRepository.findByCompanyemail(username);
		
		
		recruiter.setCity(recruiter1.getCity());
		recruiter.setCompanyname(recruiter1.getCompanyname());
		recruiter.setContact_no(recruiter1.getContact_no());
		
		this.recruiterRepository.save(recruiter);
		model.addAttribute("recruiter", recruiter);

		
		return "Recruiter/html_update_Profile";
	}
	
	@GetMapping("/postjob")
   public String postjob(Principal principle,Model model)
   {
		String username=principle.getName();
		System.out.println(username);
		Recruiter recruiter=this.recruiterRepository.findByCompanyemail(username);
		
		model.addAttribute("recruiter", recruiter);
	List<JobPost> listedjobs=recruiter.getJobPost();
		
	
	model.addAttribute("joblist", listedjobs);
	model.addAttribute("recruiter",recruiter);
	   return "Recruiter/Post_job";
   }
	
	@GetMapping("/mydashboard")
	public String showdashboard(Principal principle,Model model)
	{
		String username=principle.getName();
		System.out.println(username);
		Recruiter recruiter=this.recruiterRepository.findByCompanyemail(username);
		
	List<JobApplication> lst =this.jobApplicationRepository.findByRecruiterId(recruiter.getCid());
	int size=lst.size();
	int approved=0,rejected=0,pending=0;
	for(JobApplication x:lst)
	{
		if(x.getStatus()==1)
			approved++;
		else if(x.getStatus()==-1)
			rejected++;
		else
			pending++;
	}
	    model.addAttribute("pending",pending);
	    model.addAttribute("approved",approved);
	    model.addAttribute("rejected",rejected);
	    model.addAttribute("sz",size);
	    model.addAttribute("ja", lst);
		model.addAttribute("recruiter", recruiter);
		return "Recruiter/reruiter_dashboard";
	}
	
	@GetMapping("/addjobs")
	public String addjob(Principal principle)
	{
		String username=principle.getName();
		System.out.println(username);
		Recruiter recruiter=this.recruiterRepository.findByCompanyemail(username);
		return "Recruiter/addjob";
	}
	
	@GetMapping("/makejob")
	public String makejob(Principal principle,Model model,@ModelAttribute("post")JobPost jp)
	{
		String username=principle.getName();
		System.out.println(username);
		Recruiter recruiter=this.recruiterRepository.findByCompanyemail(username);
		 jp.setRecruiter(recruiter); 
		 recruiter.getJobPost().add(jp); 
		 this.recruiterRepository.save(recruiter);
		 
		 return "Recruiter/addjob";
	}
	
		
	@GetMapping("/Showdetails")
	public String jobDetails(Principal principle,Model model)
	{
		String username=principle.getName();
		Recruiter recruiter=this.recruiterRepository.findByCompanyemail(username);
		model.addAttribute("recruiter", recruiter);
		
		return "JobDetails";
	}
	
	@GetMapping("/info/{stId}")
	 public String  viewMe(Principal principal,Model model,@PathVariable int stId)
	 {
		 String username=principal.getName();
			System.out.println(username);
			Optional<Student> student=this.studentRepository.findById(stId);
			Student st=student.get();
		 model.addAttribute("student", st);
		 return "info";
	 }
	
	@GetMapping("/Approveupdate/{jobapplid}")
	public String ApproveupdateAppli(Principal principal,Model model,@PathVariable int jobapplid)
	{
		Optional<JobApplication> ja=this.jobApplicationRepository.findById(jobapplid);
		JobApplication jobappli=ja.get();
		jobappli.setStatus(1);
		this.jobApplicationRepository.save(jobappli);
		return "redirect:/recruiter/mydashboard";
	}
	
	@GetMapping("/Rejectupdate/{jobapplid}")
	public String RejectupdateAppli(Principal principal,Model model,@PathVariable int jobapplid)
	{
		Optional<JobApplication> ja=this.jobApplicationRepository.findById(jobapplid);
		JobApplication jobappli=ja.get();
		jobappli.setStatus(-1);
		this.jobApplicationRepository.save(jobappli);
		return "redirect:/recruiter/mydashboard";
	}
	
	
	
 }
	

