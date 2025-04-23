package com.bispo.projecting.model.service.task;

import com.bispo.projecting.dto.task.CreateTaskRequest;
import com.bispo.projecting.dto.task.TaskResponse;
import com.bispo.projecting.dto.task.UpdateTaskRequest;
import com.bispo.projecting.exception.TaskNotFoundException;
import com.bispo.projecting.exception.UserNotFoundException;
import com.bispo.projecting.model.entity.Task;
import com.bispo.projecting.model.enums.TaskStatus;
import com.bispo.projecting.model.mapper.TaskMapper;
import com.bispo.projecting.model.repository.TaskRepository;
import com.bispo.projecting.model.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService implements ITaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;

    public TaskService(
        TaskRepository taskRepository,
        UserRepository userRepository
    ) {
        this.taskMapper = TaskMapper.INSTANCE;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> create(CreateTaskRequest request) {
        try {
            var entity = taskMapper.toEntity(request);
            var user = userRepository.findById(request.userId())
                    .orElseThrow(UserNotFoundException::new);

            entity.setStartDate(LocalDate.now());
            entity.setStatus(TaskStatus.PENDING.getDescription());
            entity.setUser(user);

            var response = validateTask(entity);

            if (response.getStatusCode().isSameCodeAs(HttpStatus.OK)) {
                var responseSave = taskRepository.save(entity);
                return new ResponseEntity<>(responseSave, HttpStatus.OK);
            }

            return response;
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> update(UpdateTaskRequest request) {
        try {
            var entity = taskRepository.findById(request.id())
                    .orElseThrow(TaskNotFoundException::new);

            if (request.userId().isPresent()) {
                var user = userRepository.findById(request.userId().get())
                        .orElseThrow(UserNotFoundException::new);
                entity.setUser(user);
            }

            entity.setId(request.id());
            request.name().ifPresent(entity::setName);
            request.description().ifPresent(entity::setDescription);
            request.endDate().ifPresent(entity::setEndDate);
            request.status().ifPresent(entity::setStatus);

            var response = validateTask(entity);

            if (response.getStatusCode().isSameCodeAs(HttpStatus.OK)) {
                var responseSave = taskRepository.save(entity);
                var responseTask = taskMapper.toTaskResponse(responseSave);
                return new ResponseEntity<>(responseTask, HttpStatus.OK);
            }

            return response;
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> complete(Long id) {
        try {
            var entity = taskRepository.findById(id)
                    .orElseThrow(TaskNotFoundException::new);

            entity.setStatus(TaskStatus.COMPLETED.getDescription());
            var response = taskRepository.save(entity);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> findAll() {
        var entities = taskRepository.findAll();
        List<TaskResponse> response = new ArrayList<>();

        if (entities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        for (var entity : entities) {
            response.add(new TaskResponse(
                    entity.getId(),
                    entity.getName(),
                    entity.getDescription(),
                    entity.getStartDate(),
                    entity.getEndDate(),
                    entity.getStatus(),
                    entity.getUser().getId()
            ));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findById(Long id) {
        try {
            var entity = taskRepository.findById(id)
                    .orElseThrow(TaskNotFoundException::new);

            var response = new TaskResponse(
                    entity.getId(),
                    entity.getName(),
                    entity.getDescription(),
                    entity.getStartDate(),
                    entity.getEndDate(),
                    entity.getStatus(),
                    entity.getUser().getId()
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> remove(Long id) {
        try {
            var entity = taskRepository.findById(id)
                    .orElseThrow(TaskNotFoundException::new);

            entity.setStatus(TaskStatus.FINISHED.getDescription());
            var response = taskRepository.save(entity);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private ResponseEntity<?> validateTask(Task entity) {
        var startDate = entity.getStartDate();
        var endDate = entity.getEndDate();

        if (startDate.isAfter(endDate)) {
            return new ResponseEntity<>("Invalid dates", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
