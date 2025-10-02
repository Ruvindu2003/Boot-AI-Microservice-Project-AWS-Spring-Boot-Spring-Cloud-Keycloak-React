package com.example.ActivityService.service;

import com.example.ActivityService.dto.ActivityRequest;
import com.example.ActivityService.dto.ActivityResponce;

import java.util.List;

public interface ActivityService {
    ActivityResponce trackActivity(ActivityRequest activityRequest);

    List<ActivityResponce> getAllActivity(String usetID);
}
