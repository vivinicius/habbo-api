package com.habbo.api.controller;

import com.habbo.api.dto.LoginRequest;
import com.habbo.api.model.User;
import com.habbo.api.repository.UserRepository;
import com.habbo.api.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.habbo.api.dto.RegisterAdminRequest;
import com.habbo.api.model.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;


import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Senha inválida");
        }

        String token = JwtUtil.generateToken(
                user.getUsername(),
                user.getRole().name()
        );

        return Map.of(
                "token", token,
                "role", user.getRole().name(),
                "username", user.getUsername()
        );
    }

    @PostMapping("/admin/register")
    public ResponseEntity<?> registerAdmin(
            @RequestHeader("X-ADMIN-CODE") String adminCode,
            @RequestBody RegisterAdminRequest request
    ) {

        String envAdminCode = System.getenv("ADMIN_CODE");

        if (envAdminCode == null || !envAdminCode.equals(adminCode)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Código de administrador inválido");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest()
                    .body("Usuário já existe");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ADMIN);

        userRepository.save(user);

        return ResponseEntity.ok("Administrador criado com sucesso");
    }

}
