package com.bispo.projecting.dto.project;

import java.time.LocalDate;

public record CreateProjectRequest(
        String name,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        String client
) { }
