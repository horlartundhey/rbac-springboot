package com.rbac.rolebased.dtos;

import jakarta.validation.constraints.NotNull;

public record EnrollmentRequest(
        @NotNull(message = "Course ID is required")
        Long courseId
) {
}
