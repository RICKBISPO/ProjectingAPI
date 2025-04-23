package com.bispo.projecting.dto.task;

import java.time.LocalDate;

public record TaskResponse(
        Long id,
        String name,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        String status,
        Long userId
) { }
