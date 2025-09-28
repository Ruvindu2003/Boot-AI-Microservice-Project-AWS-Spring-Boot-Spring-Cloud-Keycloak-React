package com.example.demo.service.impl;

import com.example.demo.entity.UserResponse;
import com.example.demo.entity.RegisterequestEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

@Service

public class UserService_Impl implements UserService {


    @Override
    public UserResponse getUserProfile(Long userId) {
        return null;
    }

    @Override
    public UserResponse register(RegisterequestEntity registerequest) {

        UserEntity userEntity=new UserEntity();
        userEntity.setEmail(registerequest.getEmail());
        userEntity.setPassword(registerequest.getPassword());
        userEntity.setName(registerequest.getFirstName());
        userEntity.setLastName(registerequest.getLastName());
        return null;
    }
}
