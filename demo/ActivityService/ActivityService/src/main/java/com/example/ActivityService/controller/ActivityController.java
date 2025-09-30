package com.example.ActivityService.controller;


import com.example.ActivityService.dto.ActivityResponce;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/activities")
@RequiredArgsConstructor
@CrossOrigin

public class ActivityController {

    @PostMapping
    public ResponseEntity<ActivityResponce>  trackActivity(ActivityRequest activityRequest){
        return ResponseEntity.ok(activityService.trackActivity(activityRequest)).build();

    }
}
