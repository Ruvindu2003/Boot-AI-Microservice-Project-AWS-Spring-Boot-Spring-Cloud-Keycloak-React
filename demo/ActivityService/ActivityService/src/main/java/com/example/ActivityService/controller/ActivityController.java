package com.example.ActivityService.controller;


import com.example.ActivityService.dto.ActivityRequest;
import com.example.ActivityService.dto.ActivityResponce;
import com.example.ActivityService.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/activities")
@CrossOrigin
@RestController
public class ActivityController {

    private final ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping
    public ResponseEntity<ActivityResponce>  trackActivity(@RequestBody ActivityRequest activityRequest){
        return ResponseEntity.ok((ActivityResponce) activityService.trackActivity(activityRequest));

    }

    @GetMapping
    public  ResponseEntity<List<ActivityResponce>> getUserActivitys(@RequestHeader("x-userId") String usetID){
        return ResponseEntity.ok(activityService.getAllActivity(usetID));
    }

}
