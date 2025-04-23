package com.bispo.projecting.dto.task;

import java.time.LocalDate;

public record CreateTaskRequest(
        String name,
        String description,
        LocalDate endDate,
        Long userId
) { }
