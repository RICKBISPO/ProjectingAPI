package com.bispo.projecting.dto.auth;

public record RegisterRequest (
        String name,
        String email,
        String password
) { }
