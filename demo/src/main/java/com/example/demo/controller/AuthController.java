package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.RefreshRequest;
import com.example.demo.entity.RefreshToken;
import com.example.demo.service.RefreshTokenService;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse resp = userService.authenticate(request);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestBody RefreshRequest request) {
        RefreshToken existing = refreshTokenService.validateAndGet(request.getRefreshToken());
        var user = existing.getUser();
        String newJwt = jwtUtil.generateToken(user.getEmail(), user.getUserRole() != null ? user.getUserRole().name() : "USER");
        RefreshToken rotated = refreshTokenService.rotateToken(existing.getToken());
        LoginResponse resp = new LoginResponse(newJwt, jwtUtil.getExpirationSeconds(), user.getId(), rotated.getToken());
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody RefreshRequest request) {
        if (request == null || request.getRefreshToken() == null) return ResponseEntity.badRequest().build();
        refreshTokenService.delete(request.getRefreshToken());
        return ResponseEntity.noContent().build();
    }
}
