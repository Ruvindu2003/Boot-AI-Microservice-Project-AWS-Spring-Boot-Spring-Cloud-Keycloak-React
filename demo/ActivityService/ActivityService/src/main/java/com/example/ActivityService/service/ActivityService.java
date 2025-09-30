package com.example.ActivityService.service;

import com.example.ActivityService.dto.ActivityRequest;
import com.example.ActivityService.dto.ActivityResponce;

public interface ActivityService {
    ActivityResponce trackActivity(ActivityRequest activityRequest);
}
