package com.example.demo.service;

import com.example.demo.entity.RefreshToken;
import com.example.demo.entity.UserEntity;

public interface RefreshTokenService {
    RefreshToken createRefreshTokenFor(UserEntity user);

    RefreshToken rotateToken(String token);

    RefreshToken validateAndGet(String token);

    void delete(String token);
}

