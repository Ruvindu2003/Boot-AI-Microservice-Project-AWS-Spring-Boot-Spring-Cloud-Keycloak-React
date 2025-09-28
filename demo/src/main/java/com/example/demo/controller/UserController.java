package com.example.demo.controller;


import com.example.demo.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUsers(@PathVariable Long userId){

        return ResponseEntity.ok(userService.getUserProfile(userId));
    }

    @GetMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody Registerequest registerequest){

        return ResponseEntity.ok(request.register(registerequest));



    }


}
