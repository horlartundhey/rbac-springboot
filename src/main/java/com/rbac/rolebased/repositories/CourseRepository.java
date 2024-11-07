package com.rbac.rolebased.repositories;

import com.rbac.rolebased.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByTitle(String title);

    // Find courses by the instructor's ID
    List<Course> findAllByInstructors_Id(Long instructorId);

    // Optionally, you might want a method to find courses by their IDs, if needed
    Optional<Course> findById(Long id);
}
