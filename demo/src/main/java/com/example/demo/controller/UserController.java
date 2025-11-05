package com.example.demo.controller;


import com.example.demo.dto.RoleRequest;
import com.example.demo.entity.RegisterequestEntity;
import com.example.demo.entity.UserResponse;
import com.example.demo.model.ActivityDto;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUsers(@PathVariable Long userId){

        return ResponseEntity.ok(userService.getUserProfile(userId));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid RegisterequestEntity registerequest){

        UserResponse created = userService.register(registerequest);
        return ResponseEntity.status(201).body(created);
    }

    // Accept POST to the collection root in case clients POST to /api/users
    @PostMapping(consumes = "application/json")
    public ResponseEntity<UserResponse> registerRoot(@RequestBody @Valid RegisterequestEntity registerequest){
        return register(registerequest);
    }

    @PutMapping("/{userId}/role")
    public ResponseEntity<UserResponse> setUserRole(@PathVariable Long userId,
                                                    @RequestBody @Valid RoleRequest roleRequest,
                                                    @RequestHeader(value = "Authorization", required = false) String authorization) {
        String role = jwtUtil.getRoleFromToken(authorization);
        if (role == null || !"ADMIN".equalsIgnoreCase(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        UserResponse updated = userService.setUserRole(userId, roleRequest.getRole());
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{userId}/activities")
    public ResponseEntity<Void> postActivityForUser(@PathVariable Long userId,
                                                    @RequestBody ActivityDto activityDto,
                                                    @RequestHeader(value = "Authorization", required = false) String authorization) {
        String role = jwtUtil.getRoleFromToken(authorization);
        if (role == null || !"ADMIN".equalsIgnoreCase(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        userService.postActivityForUser(userId, activityDto);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{userId}/activities")
    public ResponseEntity<List<ActivityDto>> searchActivities(@PathVariable Long userId,
                                                              @RequestParam(value = "q", required = false) String q,
                                                              @RequestHeader(value = "Authorization", required = false) String authorization) {
        String role = jwtUtil.getRoleFromToken(authorization);
        String subject = jwtUtil.getSubjectFromToken(authorization);

        boolean allowed = false;
        if (role != null && "ADMIN".equalsIgnoreCase(role)) allowed = true;
        if (!allowed && subject != null) {
            // allow users to search their own activities
            // map subject (email) to userId
            var opt = userRepository.findByEmail(subject);
            if (opt.isPresent() && opt.get().getId().equals(userId)) allowed = true;
        }
        if (!allowed) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        List<ActivityDto> results = userService.searchActivities(userId, q);
        return ResponseEntity.ok(results);
    }

}
