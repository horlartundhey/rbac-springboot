package com.rbac.rolebased.controllers;

import com.rbac.rolebased.dtos.CreateUserRequest;
import com.rbac.rolebased.dtos.UpdateUserRequest;
import com.rbac.rolebased.dtos.UserResponse;
import com.rbac.rolebased.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/instructors")
    public ResponseEntity<UserResponse> createInstructor(@RequestBody CreateUserRequest createUserRequest) {
        try {
            UserResponse userResponse = userService.createInstructor(createUserRequest);
            return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/students")
    public ResponseEntity<UserResponse> createStudent(@RequestBody CreateUserRequest createUserRequest) {
        try {
            UserResponse userResponse = userService.createStudent(createUserRequest);
            return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest updateUserRequest) {
        try {
            String responseMessage = userService.updateUser(id, updateUserRequest);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("all")
    public ResponseEntity<List<UserResponse>> listAllUsers() {
        List<UserResponse> users = userService.listAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        try {
            UserResponse userResponse = userService.getUserById(id);
            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
