package com.rbac.rolebased.controllers;

import com.rbac.rolebased.dtos.CourseResponse;
import com.rbac.rolebased.dtos.CreateCourseRequest;
import com.rbac.rolebased.dtos.InstructorAssignmentRequest;
import com.rbac.rolebased.services.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    // Endpoint to create a new course
    @PostMapping
    public ResponseEntity<CourseResponse> createCourse(@RequestBody CreateCourseRequest createCourseRequest) {
        CourseResponse courseResponse = courseService.createCourse(createCourseRequest);
        return new ResponseEntity<>(courseResponse, HttpStatus.CREATED);
    }

    // Endpoint to assign one or more instructors to a specific course
    @PostMapping("/{courseId}/instructors")
    public ResponseEntity<Void> assignInstructorsToCourse(@PathVariable Long courseId, @RequestBody InstructorAssignmentRequest assignmentRequest) {
        courseService.assignInstructorsToCourse(courseId, assignmentRequest);
        return ResponseEntity.ok().build();
    }

    // Endpoint to get a list of all courses and their instructors
    @GetMapping
    public ResponseEntity<List<CourseResponse>> listAllCourses() {
        List<CourseResponse> courses = courseService.listAllCourses();
        return ResponseEntity.ok(courses);
    }

    // Endpoint to get a list of available courses
    @GetMapping("/available")
    public ResponseEntity<List<CourseResponse>> listAvailableCourses() {
        List<CourseResponse> availableCourses = courseService.listAvailableCourses();
        return ResponseEntity.ok(availableCourses);
    }

    // Endpoint to get a list of courses assigned to a specific instructor
    @GetMapping("/assigned")
    public ResponseEntity<List<CourseResponse>> listAssignedCourses(@RequestParam Long instructorId) {
        List<CourseResponse> assignedCourses = courseService.listAssignedCourses(instructorId);
        return ResponseEntity.ok(assignedCourses);
    }
}
