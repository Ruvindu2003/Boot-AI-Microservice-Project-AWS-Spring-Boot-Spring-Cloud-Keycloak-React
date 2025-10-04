package com.example.ActivityService.controller;


import com.example.ActivityService.dto.ActivityRequest;
import com.example.ActivityService.dto.ActivityResponce;
import com.example.ActivityService.service.ActivityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;


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

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Activity service is running");
    }

    @PostMapping
    public ResponseEntity<ActivityResponce> trackActivity(@Valid @RequestBody ActivityRequest activityRequest){
        System.out.println("Received activity request: " + activityRequest);
        ActivityResponce response = activityService.trackActivity(activityRequest);
        System.out.println("Activity tracked successfully: " + response);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponce>> getUserActivitys(@RequestHeader("x-userId") String userID){
        return ResponseEntity.ok(activityService.getAllActivity(userID));
    }


    @GetMapping("{activityId}")
    public  ResponseEntity<ActivityResponce> getActivityById(@PathVariable String activityId){
        return ResponseEntity.ok(activityService.getActivityById(activityId));
    }

}