package com.rbac.rolebased.repositories;

import com.rbac.rolebased.entities.Course;
import com.rbac.rolebased.entities.Enrollment;
import com.rbac.rolebased.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByUser(User user);

    Optional<Enrollment> findByUserAndCourse(User user, Course course);

    List<Enrollment> findByCourse(Course course);
}
