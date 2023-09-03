package com.tandp.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tandp.Dao.JobpostRepository;
import com.tandp.entities.JobApplication;
import com.tandp.entities.JobPost;
import com.tandp.entities.Student;

import jakarta.transaction.Transactional;

@Service
public class JobService {
    @Autowired
    private JobpostRepository jobPostRepository;

    @Transactional
    public void deleteJobPostWithReferences(int jobPostId) {
        JobPost jobPost = jobPostRepository.findById(jobPostId).orElse(null);
        if (jobPost != null) {
            for (JobApplication jobApplication : jobPost.getJobApplications()) {
                Student student = jobApplication.getStudent();
                student.getJobapplied().remove(jobApplication);
                jobApplication.setStudent(null);
            }
            jobPostRepository.delete(jobPost);
        }
    }
}
