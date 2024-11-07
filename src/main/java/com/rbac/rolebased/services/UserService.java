package com.rbac.rolebased.services;

import com.rbac.rolebased.dtos.CreateUserRequest;
import com.rbac.rolebased.dtos.UpdateUserRequest;
import com.rbac.rolebased.dtos.UserResponse;
import com.rbac.rolebased.entities.Role;
import com.rbac.rolebased.entities.User;
import com.rbac.rolebased.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createInstructor(CreateUserRequest createUserRequest) {
        return createUser(createUserRequest, Role.INSTRUCTOR);
    }

    public UserResponse createStudent(CreateUserRequest createUserRequest) {
        return createUser(createUserRequest, Role.USER);
    }

    private UserResponse createUser(CreateUserRequest createUserRequest, Role role) {
        if (userRepository.findByEmail(createUserRequest.email()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setFirstname(createUserRequest.firstName());
        user.setLastname(createUserRequest.lastName());
        user.setEmail(createUserRequest.email());
        user.setPassword(passwordEncoder.encode(createUserRequest.password()));
        user.setRole(role); // Set the role directly
        user.setEnabled(true);

        User savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getFirstname(),
                savedUser.getLastname(),
                savedUser.getEmail(),
                savedUser.getRole().name(),
                savedUser.isEnabled()
        );
    }

    public String updateUser(Long id, UpdateUserRequest updateUserRequest) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isEmpty()) {
            throw new RuntimeException("User not found with ID: " + id);
        }

        User userToUpdate = existingUser.get();
        userToUpdate.setFirstname(updateUserRequest.firstName());
        userToUpdate.setLastname(updateUserRequest.lastName());
        userToUpdate.setEmail(updateUserRequest.email());
        userToUpdate.setRole(updateUserRequest.role());
        userToUpdate.setEnabled(true);
        userRepository.save(userToUpdate);

        return "User Updated Successfully";
    }

    public List<UserResponse> listAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getFirstname(),
                        user.getLastname(),
                        user.getEmail(),
                        user.getRole().name(),
                        user.isEnabled()
                ))
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        return new UserResponse(
                user.get().getId(),
                user.get().getFirstname(),
                user.get().getLastname(),
                user.get().getEmail(),
                user.get().getRole().name(),
                user.get().isEnabled()
        );
    }
}
