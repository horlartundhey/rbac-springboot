package com.rbac.rolebased.dtos;

import com.rbac.rolebased.entities.EnrollmentStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record EnrollmentResponse(
        Long id,
        Long userId,
        Long courseId,
        String enrollmentDate,
        String status
) {
    // Canonical constructor with custom conversion
    public EnrollmentResponse(Long id, long userId, Long courseId, LocalDateTime enrollmentDate, EnrollmentStatus status) {
        this(id, userId, courseId,
                enrollmentDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                status.name());
    }
}
