package com.bispo.projecting.dto.task;

import java.time.LocalDate;
import java.util.Optional;

public record UpdateTaskRequest(
        Long id,
        Optional<String> name,
        Optional<String> description,
        Optional<LocalDate> endDate,
        Optional<String> status,
        Optional<Long> userId
) { }
