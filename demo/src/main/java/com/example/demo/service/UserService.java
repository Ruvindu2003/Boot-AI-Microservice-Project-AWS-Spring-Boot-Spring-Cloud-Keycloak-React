package com.example.demo.service;

import com.example.demo.entity.UserResponse;
import com.example.demo.entity.RegisterequestEntity;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.model.ActivityDto;

import java.util.List;

public interface UserService {
    UserResponse getUserProfile(Long userId) ;

    UserResponse register(RegisterequestEntity registerequest);

    Boolean exitByUserID(Long userId);

    LoginResponse authenticate(LoginRequest request);

    UserResponse setUserRole(Long userId, String role);

    void postActivityForUser(Long userId, ActivityDto activityDto);

    List<ActivityDto> searchActivities(Long userId, String q);
}
