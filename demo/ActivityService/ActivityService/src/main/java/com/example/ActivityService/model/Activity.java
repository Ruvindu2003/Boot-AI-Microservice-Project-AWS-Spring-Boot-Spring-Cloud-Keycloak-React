package com.example.ActivityService.model;

import com.example.ActivityService.util.ActivityType;

import java.time.LocalDateTime;
import java.util.Map;

public class Activity {

    private  Long id;
    private String userId;
    private ActivityType activityType;
    private Integer duratuion;
    private Integer  caloriseBurend;
    private LocalDateTime startTime;
    private LocalDateTime CreatedAt;
    private Map<String,Object>additonalMetics;
    private LocalDateTime updateAt;


}
