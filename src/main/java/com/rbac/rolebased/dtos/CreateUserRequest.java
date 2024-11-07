package com.rbac.rolebased.dtos;

import com.rbac.rolebased.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public record CreateUserRequest(
        @NotBlank(message = "first name is required")
        String firstName,

        @NotBlank(message = "last name is required")
        String lastName,

        @NotBlank(message = "email is required")
        @Email(message = "Email should be valid")
        String email,

        @NotBlank(message = "password is required")
        @Size(min = 6, message="Password should be at least 6 characters")
        String password,

        Role role
) {
}
