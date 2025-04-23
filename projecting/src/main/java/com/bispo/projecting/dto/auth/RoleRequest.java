package com.bispo.projecting.dto.auth;

import com.bispo.projecting.model.entity.UserRole;

public record RoleRequest(
        Long id,
        UserRole role
) { }
