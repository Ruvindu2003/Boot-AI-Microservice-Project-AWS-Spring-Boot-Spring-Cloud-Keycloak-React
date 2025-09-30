package com.example.ActivityService.dto;

import com.example.ActivityService.util.ActivityType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;
@Data
public class ActivityRequest {

    private Long ueerId;
    private ActivityType activityType;
    private Integer duratuion;
    private Integer  caloriseBurend;
    private LocalDateTime startTime;
    private LocalDateTime CreatedAt;
    private Map<String,Object> additonalMetics;

}
