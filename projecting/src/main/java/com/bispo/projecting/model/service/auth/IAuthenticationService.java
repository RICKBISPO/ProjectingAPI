package com.bispo.projecting.model.service.auth;

import com.bispo.projecting.dto.auth.AuthenticationRequest;
import com.bispo.projecting.dto.auth.RegisterRequest;
import com.bispo.projecting.dto.auth.RoleRequest;
import org.springframework.http.ResponseEntity;

public interface IAuthenticationService {

    ResponseEntity<?> login(AuthenticationRequest request);
    ResponseEntity<?> register(RegisterRequest request);
    ResponseEntity<?> setRole(RoleRequest request);

}
