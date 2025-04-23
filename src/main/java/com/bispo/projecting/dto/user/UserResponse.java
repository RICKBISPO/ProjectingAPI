package com.bispo.projecting.dto.user;

import java.util.Optional;

public record UserResponse(
        Long id,
        String name,
        String email,
        Optional<Long> projectId
) { }
