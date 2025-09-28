package com.example.demo.service;

import com.example.demo.dto.UserResponse;
import com.example.demo.entity.RegisterequestEntity;

public interface UserService {
    public UserResponse getUserProfile(Long userId) ;

    UserResponse register(RegisterequestEntity registerequest);
}
