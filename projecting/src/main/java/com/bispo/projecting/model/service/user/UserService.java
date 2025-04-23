package com.bispo.projecting.model.service.user;

import com.bispo.projecting.dto.user.AddProjectRequest;
import com.bispo.projecting.dto.user.UserResponse;
import com.bispo.projecting.dto.user.UpdateUserRequest;
import com.bispo.projecting.exception.ProjectNotFoundException;
import com.bispo.projecting.exception.UserNotFoundException;
import com.bispo.projecting.model.mapper.UserMapper;
import com.bispo.projecting.model.repository.ProjectRepository;
import com.bispo.projecting.model.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProjectRepository projectRepository;

    public UserService(
            UserRepository userRepository,
            ProjectRepository projectRepository
    ) {
        this.userMapper = UserMapper.INSTANCE;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public ResponseEntity<?> update(UpdateUserRequest request) {
        try {
            var entity = userRepository.findById(request.id())
                    .orElseThrow(UserNotFoundException::new);
            var users = userRepository.findAll();

            entity.setId(request.id());
            request.name().ifPresent(entity::setName);
            request.email().ifPresent(entity::setEmail);
            request.password().ifPresent(entity::setPassword);
            if (request.projectId().isPresent()) {
                var project = projectRepository.findById(request.projectId().get());
                if (project.isPresent()) {
                    entity.setProject(project.get());
                } else {
                    throw new ProjectNotFoundException();
                }
            }

            for (var user : users) {
                if (user.getEmail().equalsIgnoreCase(entity.getEmail()) && !user.getId().equals(entity.getId())) {
                    return new ResponseEntity<>("Existing email", HttpStatus.CONFLICT);
                }
            }

            var responseSave = userRepository.save(entity);
            var responseUser = userMapper.toUserResponse(responseSave);
            return new ResponseEntity<>(responseUser, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> addProject(AddProjectRequest request) {
        try {
            var user = userRepository.findById(request.userId())
                    .orElseThrow(UserNotFoundException::new);
            var project = projectRepository.findById(request.projectId())
                    .orElseThrow(ProjectNotFoundException::new);

            user.setProject(project);
            var response = userRepository.save(user);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> findAll() {
        var entities = userRepository.findAll();
        List<UserResponse> response = new ArrayList<>();

        if (entities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        for (var entity : entities) {
            response.add(new UserResponse(
                    entity.getId(),
                    entity.getName(),
                    entity.getEmail(),
                    Optional.ofNullable(entity.getProject() != null ?
                            entity.getProject().getId() :
                            null)
            ));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findByProjectId(Long id) {
        var entities = userRepository.findAll();
        List<UserResponse> response = new ArrayList<>();

        for (var entity : entities) {
            if (entity.getProject().getId().equals(id)) {
                response.add(new UserResponse(
                        entity.getId(),
                        entity.getName(),
                        entity.getEmail(),
                        Optional.of(entity.getProject().getId())
                ));
            }
        }

        if (response.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findById(Long id) {
        try {
            var entity = userRepository.findById(id)
                    .orElseThrow(UserNotFoundException::new);

            var response = new UserResponse(
                    entity.getId(),
                    entity.getName(),
                    entity.getEmail(),
                    Optional.ofNullable(entity.getProject() != null ?
                            entity.getProject().getId() :
                            null)
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> remove(Long id) {
        try {
            var entity = userRepository.findById(id)
                    .orElseThrow(UserNotFoundException::new);

            userRepository.delete(entity);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
