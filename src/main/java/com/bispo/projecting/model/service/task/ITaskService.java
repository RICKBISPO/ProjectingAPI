package com.bispo.projecting.model.service.task;

import com.bispo.projecting.dto.task.CreateTaskRequest;
import com.bispo.projecting.dto.task.UpdateTaskRequest;
import org.springframework.http.ResponseEntity;

public interface ITaskService {

    ResponseEntity<?> create(CreateTaskRequest request);
    ResponseEntity<?> update(UpdateTaskRequest request);
    ResponseEntity<?> complete(Long id);
    ResponseEntity<?> findAll();
    ResponseEntity<?> findById(Long id);
    ResponseEntity<?> remove(Long id);

}
