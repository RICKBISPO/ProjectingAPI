package com.bispo.projecting.controller;

import com.bispo.projecting.dto.project.CreateProjectRequest;
import com.bispo.projecting.dto.project.UpdateProjectRequest;
import com.bispo.projecting.model.service.project.IProjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "project")
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final IProjectService projectService;

    public ProjectController(
            IProjectService projectService
    ) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody CreateProjectRequest request) {
        return projectService.create(request);
    }

    @PutMapping
    public ResponseEntity<?> updateProject(@RequestBody UpdateProjectRequest request) {
        return projectService.update(request);
    }

    @GetMapping
    public ResponseEntity<?> findAllProjects() {
        return projectService.findAll();
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> findProjectById(@PathVariable Long projectId) {
        return projectService.findById(projectId);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> removeProject(@PathVariable Long projectId) {
        return projectService.remove(projectId);
    }

}
