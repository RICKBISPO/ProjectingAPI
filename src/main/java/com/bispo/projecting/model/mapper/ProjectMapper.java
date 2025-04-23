package com.bispo.projecting.model.mapper;

import com.bispo.projecting.dto.project.CreateProjectRequest;
import com.bispo.projecting.dto.project.ProjectResponse;
import com.bispo.projecting.model.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProjectMapper {

    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    Project toEntity(CreateProjectRequest request);

    ProjectResponse toProjectResponse(Project project);

}
