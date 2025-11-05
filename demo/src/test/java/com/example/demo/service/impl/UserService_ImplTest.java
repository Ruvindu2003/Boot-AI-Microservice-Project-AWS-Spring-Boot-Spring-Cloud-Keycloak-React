package com.example.demo.service.impl;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.entity.RegisterequestEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.entity.UserResponse;
import com.example.demo.enums.UserRole;
import com.example.demo.exception.EmailAlreadyExistsException;
import com.example.demo.exception.InvalidCredentialsException;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class UserService_ImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService_Impl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerCreatesUserWhenEmailNotExists() {
        RegisterequestEntity req = new RegisterequestEntity();
        req.setEmail("test@example.com");
        req.setPassword("password");
        req.setName("John");
        req.setLastName("Doe");

        when(userRepository.existsByEmail(req.getEmail())).thenReturn(false);

        UserEntity saved = new UserEntity();
        saved.setId(1L);
        saved.setEmail(req.getEmail());
        saved.setPassword("encoded");
        saved.setName(req.getName());
        saved.setLastName(req.getLastName());
        saved.setCreatedAt(LocalDateTime.now());
        saved.setUpdatedAt(LocalDateTime.now());
        saved.setUserRole(UserRole.USER);

        when(passwordEncoder.encode(req.getPassword())).thenReturn("encoded");
        when(userRepository.save(any())).thenReturn(saved);

        UserResponse resp = userService.register(req);

        assertThat(resp).isNotNull();
        assertThat(resp.getId()).isEqualTo(1L);
        assertThat(resp.getEmail()).isEqualTo(req.getEmail());
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void registerThrowsWhenEmailExists() {
        RegisterequestEntity req = new RegisterequestEntity();
        req.setEmail("exists@example.com");
        when(userRepository.existsByEmail(req.getEmail())).thenReturn(true);

        assertThatThrownBy(() -> userService.register(req))
                .isInstanceOf(EmailAlreadyExistsException.class)
                .hasMessageContaining("already registered");

        verify(userRepository, never()).save(any());
    }

    @Test
    void authenticateReturnsTokenOnValidCredentials() {
        String email = "login@example.com";
        String raw = "password";
        UserEntity u = new UserEntity();
        u.setId(2L);
        u.setEmail(email);
        u.setPassword("encoded");
        u.setUserRole(UserRole.USER);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(u));
        when(passwordEncoder.matches(raw, u.getPassword())).thenReturn(true);
        when(jwtUtil.generateToken(anyString(), anyString())).thenReturn("jwt-token");
        when(jwtUtil.getExpirationSeconds()).thenReturn(3600L);

        LoginRequest req = new LoginRequest();
        req.setEmail(email);
        req.setPassword(raw);

        LoginResponse resp = userService.authenticate(req);
        assertThat(resp).isNotNull();
        assertThat(resp.getToken()).isEqualTo("jwt-token");
        assertThat(resp.getUserId()).isEqualTo(2L);
    }

    @Test
    void authenticateThrowsOnInvalidPassword() {
        String email = "login@example.com";
        UserEntity u = new UserEntity();
        u.setId(3L);
        u.setEmail(email);
        u.setPassword("encoded");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(u));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        LoginRequest req = new LoginRequest();
        req.setEmail(email);
        req.setPassword("wrong");

        assertThatThrownBy(() -> userService.authenticate(req))
                .isInstanceOf(InvalidCredentialsException.class);
    }
}

