package com.bispo.projecting.controller;

import com.bispo.projecting.dto.user.AddProjectRequest;
import com.bispo.projecting.dto.user.UpdateUserRequest;
import com.bispo.projecting.model.service.user.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "user")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final IUserService userService;

    public UserController(
            IUserService userService
    ) {
        this.userService = userService;
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest request) {
        return userService.update(request);
    }

    @PostMapping("/project")
    public ResponseEntity<?> addProject(@RequestBody AddProjectRequest request) {
        return userService.addProject(request);
    }

    @GetMapping
    public ResponseEntity<?> findAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> findUsersByProjectId(@PathVariable Long projectId) {
        return userService.findByProjectId(projectId);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> findUserById(@PathVariable Long userId) {
        return userService.findById(userId);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> removeUser(@PathVariable Long userId) {
        return userService.remove(userId);
    }

}
