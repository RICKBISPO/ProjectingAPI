package com.bispo.projecting.model.service.project;

import com.bispo.projecting.dto.project.CreateProjectRequest;
import com.bispo.projecting.dto.project.UpdateProjectRequest;
import org.springframework.http.ResponseEntity;

public interface IProjectService {

    ResponseEntity<?> create(CreateProjectRequest request);
    ResponseEntity<?> update(UpdateProjectRequest request);
    ResponseEntity<?> findAll();
    ResponseEntity<?> findById(Long id);
    ResponseEntity<?> remove(Long id);

}
