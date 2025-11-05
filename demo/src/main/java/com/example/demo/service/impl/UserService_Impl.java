package com.example.demo.service.impl;

import com.example.demo.client.ActivityClient;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.entity.UserResponse;
import com.example.demo.entity.RegisterequestEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.entity.RefreshToken;
import com.example.demo.enums.UserRole;
import com.example.demo.exception.EmailAlreadyExistsException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.exception.InvalidCredentialsException;
import com.example.demo.model.ActivityDto;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RefreshTokenService;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService_Impl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final ActivityClient activityClient;
    private final RefreshTokenService refreshTokenService;

    @Override
    public UserResponse getUserProfile(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setPassword(user.getPassword());
        userResponse.setEmail(user.getEmail());
        userResponse.setName(user.getName());
        userResponse.setLastName(user.getLastName());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdatedAt(user.getUpdatedAt());

        return userResponse;
    }

    @Override
    public UserResponse register(RegisterequestEntity registerequest) {
        if (userRepository.existsByEmail(registerequest.getEmail())) {
            throw new EmailAlreadyExistsException("Email is already registered: " + registerequest.getEmail());
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(registerequest.getEmail());
        userEntity.setPassword(passwordEncoder.encode(registerequest.getPassword()));
        userEntity.setName(registerequest.getName());
        userEntity.setLastName(registerequest.getLastName());
        userEntity.setUserRole(UserRole.USER);

        UserEntity savedUser = userRepository.save(userEntity);
        UserResponse userResponse = new UserResponse();

        userResponse.setId(savedUser.getId());
        userResponse.setPassword(savedUser.getPassword());
        userResponse.setEmail(savedUser.getEmail());
        userResponse.setName(savedUser.getName());
        userResponse.setLastName(savedUser.getLastName());
        userResponse.setCreatedAt(savedUser.getCreatedAt());
        userResponse.setUpdatedAt(savedUser.getUpdatedAt());

        return userResponse;
    }

    @Override
    public Boolean exitByUserID(Long userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public LoginResponse authenticate(LoginRequest request) {
        Optional<UserEntity> maybe = userRepository.findByEmail(request.getEmail());
        UserEntity user = maybe.orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getUserRole() != null ? user.getUserRole().name() : "USER");
        RefreshToken rt = refreshTokenService.createRefreshTokenFor(user);
        return new LoginResponse(token, jwtUtil.getExpirationSeconds(), user.getId(), rt.getToken());
    }

    @Override
    public UserResponse setUserRole(Long userId, String role) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        try {
            UserRole r = UserRole.valueOf(role.toUpperCase());
            user.setUserRole(r);
            UserEntity saved = userRepository.save(user);
            UserResponse resp = new UserResponse();
            resp.setId(saved.getId());
            resp.setEmail(saved.getEmail());
            resp.setName(saved.getName());
            resp.setLastName(saved.getLastName());
            resp.setCreatedAt(saved.getCreatedAt());
            resp.setUpdatedAt(saved.getUpdatedAt());
            return resp;
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
    }

    @Override
    public void postActivityForUser(Long userId, ActivityDto activityDto) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        // ensure activity has correct user id
        activityDto.setUserId(String.valueOf(user.getId()));
        activityClient.postActivity(activityDto);
    }

    @Override
    public List<ActivityDto> searchActivities(Long userId, String q) {
        // Delegate to activity service; if it fails, return empty list
        return activityClient.searchActivities(userId, q);
    }
}
