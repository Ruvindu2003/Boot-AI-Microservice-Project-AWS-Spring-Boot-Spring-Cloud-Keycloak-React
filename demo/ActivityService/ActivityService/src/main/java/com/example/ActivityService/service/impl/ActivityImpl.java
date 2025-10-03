package com.example.ActivityService.service.impl;

import com.example.ActivityService.dto.ActivityRequest;
import com.example.ActivityService.dto.ActivityResponce;
import com.example.ActivityService.model.Activity;
import com.example.ActivityService.repository.ActivityRepository;
import com.example.ActivityService.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityImpl implements ActivityService {
    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public ActivityResponce trackActivity(ActivityRequest activityRequest) {
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