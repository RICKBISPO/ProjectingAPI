package com.bispo.projecting.dto.user;

import java.util.Optional;

public record UpdateUserRequest(
        Long id,
        Optional<String> name,
        Optional<String> email,
        Optional<String> password,
        Optional<Long> projectId
) { }
