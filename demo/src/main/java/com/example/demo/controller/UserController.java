package com.example.demo.controller;


import com.example.demo.entity.RegisterequestEntity;
import com.example.demo.entity.UserResponse;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor

public class UserController {
    private  final  UserService userService;





    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUsers(@PathVariable Long userId){

        return ResponseEntity.ok(userService.getUserProfile(userId));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid RegisterequestEntity registerequest){

        return ResponseEntity.ok(userService.register(registerequest));



    }

   @GetMapping("/string")
    public  String get(){
        return "Fuck";
   }



}
