package com.rbac.rolebased.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCourseRequest(
        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Description is required")
        @Size(max = 1000, message = "Description should not exceed 1000 characters")
        String description
) {
}
