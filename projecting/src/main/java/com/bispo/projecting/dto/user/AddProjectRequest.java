package com.bispo.projecting.dto.user;

public record AddProjectRequest(
        Long userId,
        Long projectId
) { }
