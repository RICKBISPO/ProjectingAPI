package com.bispo.projecting.controller;

import com.bispo.projecting.dto.task.CreateTaskRequest;
import com.bispo.projecting.dto.task.UpdateTaskRequest;
import com.bispo.projecting.model.service.task.ITaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "task")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final ITaskService taskService;

    public TaskController(
            ITaskService taskService
    ) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody CreateTaskRequest request) {
        return taskService.create(request);
    }

    @PutMapping
    public ResponseEntity<?> updateTask(@RequestBody UpdateTaskRequest request) {
        return taskService.update(request);
    }

    @PostMapping("/complete")
    public ResponseEntity<?> completeTask(@PathVariable Long taskId) {
        return taskService.complete(taskId);
    }

    @GetMapping
    public ResponseEntity<?> findAllTasks() {
        return taskService.findAll();
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<?> findTaskById(@PathVariable Long taskId) {
        return taskService.findById(taskId);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> removeTask(@PathVariable Long taskId) {
        return taskService.remove(taskId);
    }

}
