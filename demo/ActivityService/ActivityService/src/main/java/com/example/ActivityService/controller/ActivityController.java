package com.example.ActivityService.controller;


import com.example.ActivityService.dto.ActivityRequest;
import com.example.ActivityService.dto.ActivityResponce;
import com.example.ActivityService.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/activities")
@RequiredArgsConstructor
@CrossOrigin

public class ActivityController {

    private final ActivityService activityService;

    @PostMapping
    public ResponseEntity<ActivityResponce>  trackActivity(ActivityRequest activityRequest){
        return ResponseEntity.ok((ActivityResponce) activityService.trackActivity(activityRequest));

    }
}
