package com.rbac.rolebased.services;

import com.rbac.rolebased.dtos.EnrollmentResponse;
import com.rbac.rolebased.entities.Course;
import com.rbac.rolebased.entities.Enrollment;
import com.rbac.rolebased.entities.EnrollmentStatus;
import com.rbac.rolebased.entities.User;
import com.rbac.rolebased.repositories.CourseRepository;
import com.rbac.rolebased.repositories.EnrollmentRepository;
import com.rbac.rolebased.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private  final UserRepository userRepository;
    private  final CourseRepository courseRepository;

    public EnrollmentResponse enrollUserInCourse(Long userId, Long courseId){
//        Retrieve the user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        if (enrollmentRepository.findByUserAndCourse(user, course).isPresent()){
            throw new RuntimeException("User is already enrolled in this course");
        }
        Enrollment enrollment = new Enrollment();
        enrollment.setUser(user);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(LocalDateTime.now());
        enrollment.setStatus(EnrollmentStatus.valueOf("enrolled"));
        
//        let's save the enrollment
        enrollment = enrollmentRepository.save(enrollment);
        
        return mapToResponse(enrollment);
    }

    public List<EnrollmentResponse> getCourseEnrollments(Long courseId) {
        // Retrieve the course
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // Retrieve all enrollments for this course
        List<Enrollment> enrollments = enrollmentRepository.findByCourse(course);

        // Map to response DTOs
        return enrollments.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    private EnrollmentResponse mapToResponse(Enrollment enrollment) {
        return new EnrollmentResponse(
                enrollment.getId(),
                enrollment.getUser().getId(),
                enrollment.getCourse().getId(),
                enrollment.getEnrollmentDate(),
                enrollment.getStatus()
        );
    }
}
