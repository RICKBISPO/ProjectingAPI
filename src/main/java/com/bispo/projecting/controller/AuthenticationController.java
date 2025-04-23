package com.bispo.projecting.controller;

import com.bispo.projecting.dto.auth.AuthenticationRequest;
import com.bispo.projecting.dto.auth.RegisterRequest;
import com.bispo.projecting.dto.auth.RoleRequest;
import com.bispo.projecting.model.service.auth.IAuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "auth")
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final IAuthenticationService authenticationService;

    public AuthenticationController(
            IAuthenticationService authenticationService
    ) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        return authenticationService.login(request);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        return authenticationService.register(request);
    }

    @PostMapping("/role")
    public ResponseEntity<?> setRole(@RequestBody RoleRequest request) {
        return authenticationService.setRole(request);
    }

}
