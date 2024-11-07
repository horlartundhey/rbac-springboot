package com.rbac.rolebased.dtos;

public record UserResponse(
        long id,
        String firstname,
        String lastname,
        String email,
        String role,
        Boolean enabled
) {
}
