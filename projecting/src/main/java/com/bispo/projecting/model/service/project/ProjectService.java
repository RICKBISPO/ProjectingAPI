package com.bispo.projecting.model.service.project;

import com.bispo.projecting.dto.project.CreateProjectRequest;
import com.bispo.projecting.dto.project.ProjectResponse;
import com.bispo.projecting.dto.project.UpdateProjectRequest;
import com.bispo.projecting.exception.ProjectNotFoundException;
import com.bispo.projecting.model.entity.Project;
import com.bispo.projecting.model.enums.ProjectStatus;
import com.bispo.projecting.model.mapper.ProjectMapper;
import com.bispo.projecting.model.repository.ProjectRepository;
import com.bispo.projecting.model.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService implements IProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserRepository userRepository;

    public ProjectService(
            ProjectRepository projectRepository,
            UserRepository userRepository
    ) {
        this.projectMapper = ProjectMapper.INSTANCE;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> create(CreateProjectRequest request) {
        var entity = projectMapper.toEntity(request);

        var response = validateProject(entity);

        if (response.getStatusCode().isSameCodeAs(HttpStatus.OK)) {
            entity.setStatus(getProjectStatus(entity).getDescription());
            var responseSave = projectRepository.save(entity);
            return new ResponseEntity<>(responseSave, HttpStatus.OK);
        }

        return response;
    }

    @Override
    public ResponseEntity<?> update(UpdateProjectRequest request) {
        try {
            var entity = projectRepository.findById(request.id())
                .orElseThrow(ProjectNotFoundException::new);

            entity.setId(request.id());
            request.name().ifPresent(entity::setName);
            request.description().ifPresent(entity::setDescription);
            request.startDate().ifPresent(entity::setStartDate);
            request.endDate().ifPresent(entity::setEndDate);
            request.client().ifPresent(entity::setClient);

            var response = validateProject(entity);

            if (response.getStatusCode().isSameCodeAs(HttpStatus.OK)) {
                entity.setStatus(getProjectStatus(entity).getDescription());
                var responseSave = projectRepository.save(entity);
                var responseProject = projectMapper.toProjectResponse(responseSave);
                return new ResponseEntity<>(responseProject, HttpStatus.OK);
            }

            return response;
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> findAll() {
        var entities = projectRepository.findAll();
        List<ProjectResponse> response = new ArrayList<>();

        if (entities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        for (var entity : entities) {
            response.add(new ProjectResponse(
                    entity.getId(),
                    entity.getName(),
                    entity.getDescription(),
                    entity.getStartDate(),
                    entity.getEndDate(),
                    entity.getStatus(),
                    entity.getClient()
            ));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findById(Long id) {
        try {
            var entity = projectRepository.findById(id)
                    .orElseThrow(ProjectNotFoundException::new);

            var response = new ProjectResponse(
                    entity.getId(),
                    entity.getName(),
                    entity.getDescription(),
                    entity.getStartDate(),
                    entity.getEndDate(),
                    entity.getStatus(),
                    entity.getClient()
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> remove(Long id) {
        try {
            var entity = projectRepository.findById(id)
                    .orElseThrow(ProjectNotFoundException::new);

            var users = userRepository.findAllUsersByProject(entity);
            if (!users.isEmpty()) {
                return new ResponseEntity<>("Project has users", HttpStatus.BAD_REQUEST);
            }

            projectRepository.delete(entity);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private ResponseEntity<?> validateProject(Project entity) {
        var projects = projectRepository.findAll();
        var startDate = entity.getStartDate();
        var endDate = entity.getEndDate();

        for (var project : projects) {
            if (project.getName().equalsIgnoreCase(entity.getName()) && !project.getId().equals(entity.getId())) {
                return new ResponseEntity<>("Existing name", HttpStatus.CONFLICT);
            }
        }

        if (!startDate.isBefore(endDate)) {
            return new ResponseEntity<>("Invalid dates", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ProjectStatus getProjectStatus(Project entity) {
        var startDate = entity.getStartDate();
        var endDate = entity.getEndDate();
        var today = LocalDate.now();

        if (today.isAfter(endDate)) {
            return ProjectStatus.FINISHED;
        }
        if (!today.isBefore(startDate)) {
            return ProjectStatus.IN_PROGRESS;
        }
        return ProjectStatus.TO_START;
    }

}
