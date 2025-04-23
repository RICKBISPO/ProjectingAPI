package com.bispo.projecting.dto.auth;

public record AuthenticationRequest(
        String email,
        String password
) { }
