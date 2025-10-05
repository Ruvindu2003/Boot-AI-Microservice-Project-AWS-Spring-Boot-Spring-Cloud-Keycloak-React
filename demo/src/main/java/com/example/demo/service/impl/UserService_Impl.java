package com.example.demo.service.impl;

import com.example.demo.entity.UserResponse;
import com.example.demo.entity.RegisterequestEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UserService_Impl implements UserService {
    private  final UserRepository userRepository;


    @Override
    public UserResponse getUserProfile(Long userId) {
        UserEntity user= (UserEntity) userRepository.findById(userId).
                orElseThrow(()->new RuntimeException("User Not Found"));
        UserResponse userResponse=new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setPassword(user.getPassword());
        userResponse.setEmail(user.getEmail());
        userResponse.setName(user.getName());
        userResponse.setLastName(user.getLastName());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdatedAt(user.getUpdatedAt());

    return  userResponse;


    }

    @Override
    public UserResponse register(RegisterequestEntity registerequest) {
        if (userRepository.existsByEmail(registerequest.getEmail())){
            throw  new RuntimeException("Email is All Rady Exit");
        }

        UserEntity userEntity=new UserEntity();
        userEntity.setEmail(registerequest.getEmail());
        userEntity.setPassword(registerequest.getPassword());
        userEntity.setName(registerequest.getName());
        userEntity.setLastName(registerequest.getLastName());
        UserEntity saveUser=  userRepository.save(userEntity);
        UserResponse userResponse=new UserResponse();

      userResponse.setId(saveUser.getId());
      userResponse.setPassword(saveUser.getPassword());
      userResponse.setEmail(saveUser.getEmail());
      userResponse.setName(saveUser.getName());
      userResponse.setLastName(saveUser.getLastName());
      userResponse.setCreatedAt(saveUser.getCreatedAt());
      userResponse.setUpdatedAt(saveUser.getUpdatedAt());

      return  userResponse;



    }

    @Override
    public Boolean exitByUserID(Long userId) {
        return  userRepository.existsById(userId);
    }
}
