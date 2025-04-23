package com.bispo.projecting.model.mapper;

import com.bispo.projecting.dto.task.CreateTaskRequest;
import com.bispo.projecting.dto.task.TaskResponse;
import com.bispo.projecting.model.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@Mapper
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    Task toEntity(CreateTaskRequest request);

    @Mapping(target = "userId", source = "user.id")
    TaskResponse toTaskResponse(Task task);

    default Optional<Long> map(Long value) {
        return Optional.ofNullable(value);
    }

}
