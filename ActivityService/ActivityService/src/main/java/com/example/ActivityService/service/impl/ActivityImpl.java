package com.example.ActivityService.service.impl;

import com.example.ActivityService.dto.ActivityRequest;
import com.example.ActivityService.dto.ActivityResponce;
import com.example.ActivityService.model.Activity;
import com.example.ActivityService.repository.ActivityRepository;
import com.example.ActivityService.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityImpl implements ActivityService {
    @Autowired
    private final ActivityRepository activityRepository;
    @Autowired
    private final UserValidation userValidation;



    @Override
    public ActivityResponce trackActivity(ActivityRequest activityRequest) {
        System.out.println("Validating user: " + activityRequest.getUserId());
        boolean isValidUser = userValidation.validateUser(activityRequest.getUserId());
        System.out.println("User validation result: " + isValidUser);
        if (!isValidUser) {
            throw new RuntimeException("Invalid User ID: " + activityRequest.getUserId());
        }
        Activity activity = Activity.builder().
                userId(activityRequest.getUserId()).
                activityType(activityRequest.getActivityType()).
                duration(activityRequest.getDuration()).
                caloriesBurned(activityRequest.getCaloriesBurned()).
                startTime(activityRequest.getStartTime()).
                createdAt(activityRequest.getCreatedAt()).
                additionalMetrics(activityRequest.getAdditionalMetrics()).build();

        Activity saveActivity = activityRepository.save(activity);
        return mapToActivityResponce(saveActivity);

    }

    @Override
    public List<ActivityResponce> getAllActivity(String userID) {
        List<Activity> activities = activityRepository.findByUserId(userID);
        return activities.stream().map(this::mapToActivityResponce).toList();

    }

    @Override
    public ActivityResponce getActivityById(String activityId) {
        Activity activity = activityRepository.findById(activityId).
                orElseThrow(() -> new RuntimeException("Activity Not Found"));
        return mapToActivityResponce(activity);
    }

    private ActivityResponce mapToActivityResponce(Activity saveActivity) {
        ActivityResponce activityResponce = new ActivityResponce();
        activityResponce.setId(saveActivity.getId());
        activityResponce.setUserId(saveActivity.getUserId());
        activityResponce.setActivityType(saveActivity.getActivityType());
        activityResponce.setDuration(saveActivity.getDuration());
        activityResponce.setCaloriesBurned(saveActivity.getCaloriesBurned());
        activityResponce.setStartTime(saveActivity.getStartTime());
        activityResponce.setCreatedAt(saveActivity.getCreatedAt());
        activityResponce.setAdditionalMetrics(saveActivity.getAdditionalMetrics());
        activityResponce.setUpdatedAt(saveActivity.getUpdatedAt());
        return activityResponce;

    }
}