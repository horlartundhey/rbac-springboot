package com.rbac.rolebased.services;

import com.rbac.rolebased.dtos.CourseResponse;
import com.rbac.rolebased.dtos.CreateCourseRequest;
import com.rbac.rolebased.dtos.InstructorAssignmentRequest;
import com.rbac.rolebased.entities.Course;
import com.rbac.rolebased.entities.User;
import com.rbac.rolebased.repositories.CourseRepository;
import com.rbac.rolebased.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private  final UserRepository userRepository;

    public CourseResponse createCourse(CreateCourseRequest createCourseRequest){
//        check if course exist by id
        if(courseRepository.findByTitle(createCourseRequest.title()).isPresent()){
            throw new RuntimeException("Course already exist");
        }

        Course course = new Course();
        course.setTitle(createCourseRequest.title());
        course.setDescription(createCourseRequest.description());

        Course savedCourse = courseRepository.save(course);

        return new CourseResponse(
                savedCourse.getId(),
                savedCourse.getTitle(),
                savedCourse.getDescription()
        );
    }

    public void assignInstructorsToCourse(Long courseId, InstructorAssignmentRequest assignmentRequest){
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if(courseOptional.isEmpty()){
            throw new RuntimeException("Course not found");
        }
        Course course = courseOptional.get();
        List<User> instructors = userRepository.findAllById(assignmentRequest.getInstructorIds())
                .stream()
                .filter(user -> user.getRole().equals("INSTRUCTOR"))
                .collect(Collectors.toList());

//        course.setInstructors(instructors);
        courseRepository.save(course);
    }

    public List<CourseResponse> listAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(course -> new CourseResponse(
                        course.getId(),
                        course.getTitle(),
                        course.getDescription()))
                .collect(Collectors.toList());
    }

    public List<CourseResponse> listAvailableCourses() {
        return courseRepository.findAll()
                .stream()
                .filter(course -> course.getInstructors().isEmpty()) // Adjust condition as needed
                .map(course -> new CourseResponse(
                        course.getId(),
                        course.getTitle(),
                        course.getDescription()))
                .collect(Collectors.toList());
    }

    public List<CourseResponse> listAssignedCourses(Long instructorId) {
        return courseRepository.findAllByInstructors_Id(instructorId) // Adjust logic based on your current implementation
                .stream()
                .map(course -> new CourseResponse(
                        course.getId(),
                        course.getTitle(),
                        course.getDescription()))
                .collect(Collectors.toList());
    }
}
