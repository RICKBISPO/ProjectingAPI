package com.bispo.projecting.dto.project;

import java.time.LocalDate;
import java.util.Optional;

public record UpdateProjectRequest(
        Long id,
        Optional<String> name,
        Optional<String> description,
        Optional<LocalDate> startDate,
        Optional<LocalDate> endDate,
        Optional<String> client
) { }
