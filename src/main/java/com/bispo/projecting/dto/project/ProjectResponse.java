package com.bispo.projecting.dto.project;

import java.time.LocalDate;

public record ProjectResponse(
        Long id,
        String name,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        String status,
        String client
) { }
