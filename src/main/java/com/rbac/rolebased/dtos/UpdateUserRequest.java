package com.rbac.rolebased.dtos;

import com.rbac.rolebased.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(
        @NotBlank(message = "first name is required")
        String firstName,

        @NotBlank(message = "last name is required")
        String lastName,

        @NotBlank(message = "email is required")
        @Email(message = "Email should be valid")
        String email,


        Role role,
        Boolean enabled
) {
}
