package com.bispo.projecting.model.service.auth;

import com.bispo.projecting.dto.auth.AuthenticationRequest;
import com.bispo.projecting.dto.auth.AuthenticationResponse;
import com.bispo.projecting.dto.auth.RegisterRequest;
import com.bispo.projecting.dto.auth.RoleRequest;
import com.bispo.projecting.model.entity.User;
import com.bispo.projecting.model.entity.UserRole;
import com.bispo.projecting.model.mapper.UserMapper;
import com.bispo.projecting.model.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationService implements IAuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final UserMapper userMapper;

    public AuthenticationService(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            TokenService tokenService
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.userMapper = UserMapper.INSTANCE;
    }

    @Override
    public ResponseEntity<?> login(AuthenticationRequest request) {
        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
        );
        var authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        var token = tokenService.generateToken((User) authentication.getPrincipal());

        return new ResponseEntity<>(new AuthenticationResponse(token),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> register(RegisterRequest request) {
        if (this.userRepository.findByEmail(request.email()) != null) {
            return new ResponseEntity<>("Existing email", HttpStatus.CONFLICT);
        }

        RegisterRequest updatedRequest = new RegisterRequest(
                request.name(),
                request.email(),
                new BCryptPasswordEncoder().encode(request.password())
        );

        var entity = userMapper.toEntity(updatedRequest);
        entity.setRole(UserRole.USER);
        var responseSave = (this.userRepository.save(entity));
        return new ResponseEntity<>(responseSave, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> setRole(RoleRequest request) {
        var entity = userRepository.findById(request.id());

        if (entity.isPresent()) {
            entity.get().setRole(request.role());
            return new ResponseEntity<>(entity.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>("Existing user", HttpStatus.BAD_REQUEST);
    }

}
