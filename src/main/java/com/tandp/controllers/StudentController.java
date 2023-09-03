package com.tandp.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tandp.Dao.JobApplicationRepository;
import com.tandp.Dao.JobpostRepository;
import com.tandp.Dao.StudentRepository;
//import com.tandp.Dao.JobApplicationRepository;
//import com.tandp.Dao.JobpostRepository;
//import com.tandp.Dao.StudentRepository;
import com.tandp.entities.JobApplication;
import com.tandp.entities.JobPost;
import com.tandp.entities.Recruiter;
import com.tandp.entities.Student;
import com.tandp.helper.Messages;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/student")
public class StudentController {
  
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private JobpostRepository jobpostRepository;
	
	@Autowired
	private JobApplicationRepository jobApplicationRepository;
	
	
	
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

	public StudentRepository getStudentRepository() {
		return studentRepository;
	}

	public void setStudentRepository(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	
	
	
	@PostMapping("/processprofile")
	public String processprofile(@ModelAttribute("student") Student student,
			Principal principle,
			@RequestParam("imges") MultipartFile file1,
			@RequestParam("resumes") MultipartFile file2,
			Model model,
			HttpSession session)
	{
		
	
		try {
		
	         String name=principle.getName();
	         Student st=this.studentRepository.findByEmail(name);
              System.out.println(st.getEmail());
		  
              if(!file1.isEmpty() && !file2.isEmpty())
              {
             
            	  st.setImg(file1.getOriginalFilename());
            	  st.setResume(file2.getOriginalFilename());
            File file3=new ClassPathResource("static/images").getFile();
            File file4=new ClassPathResource("static/js").getFile();
        Path p1 = Paths.get(file3.getAbsolutePath()+File.separator+file1.getOriginalFilename());
        Path p2 = Paths.get(file4.getAbsolutePath()+File.separator+file2.getOriginalFilename());
            Files.copy(file1.getInputStream(), p1, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(file2.getInputStream(), p2, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Image is Uplode");
              }
              
                st.setContact(student.getContact());
                st.setBranch(student.getBranch());
                st.setExp(student.getExp());
                st.setName(student.getName());
                st.setYear(student.getYear());
                st.setScore(student.getScore());
                st.setSkills(student.getSkills());
                
                studentRepository.save(st);
                
                session.setAttribute("message",new Messages("Profile Updated Succesfully !!","success"));
		}catch(Exception e)
		{
			session.setAttribute("message",new Messages("Something Went Wrong !!","danger"));
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		
		
     

		return "Student/html_update_Profile";
	}
	
	
	
	
	
	@GetMapping("/mydashboard")
	public String mydash( Principal principle,Model model)
	{
	
		String username=principle.getName();
		
		System.out.println(username);
		
		Student student=this.studentRepository.findByEmail(username);
		List<JobApplication> jl=student.getJobapplied();
		int size=jl.size();
		int approved=0,rejected=0,pending=0;
		for(JobApplication x:jl)
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
		model.addAttribute("joblist", jl);
		model.addAttribute("student", student);
		
		return "Student/Main_Dashboard";
	}
	
	
	@GetMapping("/update")
	public String update( Principal principle,Model model)
	{
		String username=principle.getName();
		System.out.println(username);
		Student student=this.studentRepository.findByEmail(username);
		model.addAttribute("student", student);
		
		return "Student/html_update_Profile";
	}
	
	
	@GetMapping("/applyjob")
	public String applyjob(Principal principle,Model model)
	{
		String username=principle.getName();
		System.out.println(username);
		Student student=this.studentRepository.findByEmail(username);
		List<JobPost> joblist=this.jobpostRepository.findAll();
		List<JobApplication> ja=this.jobApplicationRepository.findAll();
		model.addAttribute("ja",ja);
		model.addAttribute("joblist",joblist);
		model.addAttribute("student",student);
		return "Student/html_apply_job";
	}
	
	@GetMapping("/Viewjobdesc/{jobid}")
	public String viewJob(Model model,@PathVariable int jobid)
	{
		Optional<JobPost> jp=this.jobpostRepository.findById(jobid);
		JobPost j=jp.get();
		model.addAttribute("jp", j);
		return "Student/viewjobdesc";
	}
	
	@PostMapping("/saveprofile")
	public String saveMe(Principal principle,Model model,@RequestParam("img") MultipartFile file,@RequestParam("resume") MultipartFile file1)
	{
		
		try
		{
		String username=principle.getName();
		System.out.println(username);
		Student student=this.studentRepository.findByEmail(username);
		model.addAttribute("student", student);
		}
		catch(Exception e)
		{
			
		}
		
		
		
		return "Student/html_update_Profile";
		
	}
	
	@GetMapping("/submitjob/{stid}/{jobid}")
	public String SubmitJob(Model model,@PathVariable int stid,@PathVariable int jobid,HttpSession session)
	{
		try {
			
		
		Optional<JobPost> jp=this.jobpostRepository.findById(jobid);
		JobPost j=jp.get();
		JobApplication ja=new JobApplication();
	    j.getJobApplications().add(ja);
		ja.setPost(j);
		Optional<Student> st=this.studentRepository.findById(stid);
		Student student=st.get();
		ja.setStudent(student);	
		student.getJobapplied().add(ja);
		this.studentRepository.save(student);
		/* this.jobApplicationRepository.save(ja); */
		model.addAttribute("jp", j);
		 session.setAttribute("message",new Messages("Profile Updated Succesfully !!","success"));
		}catch(Exception e)
		{
			session.setAttribute("message",new Messages("Something Went Wrong !!","danger"));
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/student/mydashboard";
	}
	
	
	 @GetMapping("/Myinfo")
	 public String  viewMe(Principal principal,Model model)
	 {
		 String username=principal.getName();
			System.out.println(username);
			Student student=this.studentRepository.findByEmail(username);
		 model.addAttribute("student", student);
		 return "info";
	 }
	 
}
	
	

	

