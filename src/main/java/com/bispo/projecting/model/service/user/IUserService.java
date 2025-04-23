package com.bispo.projecting.model.service.user;

import com.bispo.projecting.dto.user.AddProjectRequest;
import com.bispo.projecting.dto.user.UpdateUserRequest;
import org.springframework.http.ResponseEntity;

public interface IUserService {

    ResponseEntity<?> update(UpdateUserRequest request);
    ResponseEntity<?> addProject(AddProjectRequest request);
    ResponseEntity<?> findAll();
    ResponseEntity<?> findByProjectId(Long id);
    ResponseEntity<?> findById(Long id);
    ResponseEntity<?> remove(Long id);

}
